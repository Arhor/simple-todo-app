package dev.arhor.simple.todo.infrastructure.aspect;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.arhor.simple.todo.infrastructure.context.CurrentRequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MethodExecutionLoggingAspect {

    private final CurrentRequestContext currentRequestContext;

    @Pointcut("execution(* dev.arhor.simple.todo.web.controller..*(..))")
    private void webLayer() { /* no-op */ }

    @Pointcut("execution(* dev.arhor.simple.todo.service..*(..))")
    private void serviceLayer() { /* no-op */ }

    @Pointcut("execution(* dev.arhor.simple.todo.data..*(..))")
    private void persistenceLayer() { /* no-op */ }

    @Before(value = "webLayer() || serviceLayer() || persistenceLayer()")
    public void intercept(final JoinPoint joinPoint) {
        if (log.isInfoEnabled()) {
            var requestId = currentRequestContext.getRequestId();
            var signature = stringifyMethodExecutionSignature(joinPoint);

            log.info("Request-ID: {}, Method: {}", requestId, signature);
        }
    }

    private String stringifyMethodExecutionSignature(final JoinPoint joinPoint) {
        var className = joinPoint.getSourceLocation().getWithinType().getCanonicalName();
        var methodName = joinPoint.getSignature().getName();
        var methodArgs = StringUtils.joinWith(",", joinPoint.getArgs());

        return className + "." + methodName + "(" + methodArgs + ")";
    }
}
