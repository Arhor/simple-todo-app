package dev.arhor.simple.todo.config;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.context.request.RequestContextHolder;

@EnableAsync
@Configuration(proxyBeanMethods = false)
public class AsyncConfig implements AsyncConfigurer {

    @Bean
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }

    @Bean
    @Override
    public Executor getAsyncExecutor() {
        return new DelegatingSecurityContextAsyncTaskExecutor(
            new ContextAwareThreadPoolTaskExecutor()
        );
    }

    private static class ContextAwareThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

        @Override
        public <T> Future<T> submit(final Callable<T> task) {
            return super.submit(contextAwareCallable(task));
        }

        @Override
        public <T> ListenableFuture<T> submitListenable(final Callable<T> task) {
            return super.submitListenable(contextAwareCallable(task));
        }

        @Override
        public Future<?> submit(final Runnable task) {
            return super.submit(contextAwareRunnable(task));
        }

        @Override
        public ListenableFuture<?> submitListenable(final Runnable task) {
            return super.submitListenable(contextAwareRunnable(task));
        }

        @Override
        public void execute(final Runnable task) {
            super.execute(contextAwareRunnable(task));
        }

        @Override
        public void execute(final Runnable task, final long startTimeout) {
            super.execute(contextAwareRunnable(task), startTimeout);
        }

        private <T> Callable<T> contextAwareCallable(final Callable<T> task) {
            var parentThreadRequestAttributes = RequestContextHolder.currentRequestAttributes();
            return () -> {
                try {
                    RequestContextHolder.setRequestAttributes(parentThreadRequestAttributes);
                    return task.call();
                } finally {
                    RequestContextHolder.resetRequestAttributes();
                }
            };
        }

        private Runnable contextAwareRunnable(final Runnable task) {
            var parentThreadRequestAttributes = RequestContextHolder.currentRequestAttributes();
            return () -> {
                try {
                    RequestContextHolder.setRequestAttributes(parentThreadRequestAttributes);
                    task.run();
                } finally {
                    RequestContextHolder.resetRequestAttributes();
                }
            };
        }
    }
}
