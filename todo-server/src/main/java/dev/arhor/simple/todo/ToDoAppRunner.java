package dev.arhor.simple.todo;

import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import dev.arhor.simple.todo.infrastructure.startup.StartupTask;

@SpringBootApplication(proxyBeanMethods = false)
public class ToDoAppRunner {

    public static void main(String[] args) {
        SpringApplication.run(ToDoAppRunner.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(final List<StartupTask> startupTasks) {
        return args -> startupTasks.stream().sorted().forEach(StartupTask::execute);
    }
}
