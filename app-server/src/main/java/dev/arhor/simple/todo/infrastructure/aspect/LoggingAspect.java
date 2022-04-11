package dev.arhor.simple.todo.infrastructure.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.arhor.simple.todo.infrastructure.context.CurrentRequestContext;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LoggingAspect {

    private final CurrentRequestContext currentRequestContext;

    @Around("webLayer() || serviceLayer() || persistenceLayer()")
    public Object logMethodExecution(final ProceedingJoinPoint joinPoint) throws Throwable {
        var log = componentLogger(joinPoint);
        if (log.isDebugEnabled()) {
            var requestId = currentRequestContext.getRequestId();
            var signature = joinPoint.getSignature();
            var signatureName = signature.getDeclaringType().getSimpleName() + "." + signature.getName() + "()";
            log.debug(
                "Request-ID: {}, Method: {}, Arguments: {}",
                requestId,
                signatureName,
                Arrays.toString(joinPoint.getArgs())
            );
            Object result = joinPoint.proceed();
            log.debug(
                "Request-ID: {}, Method: {}, Result: {}",
                requestId,
                signatureName,
                result
            );
            return result;
        } else {
            return joinPoint.proceed();
        }
    }

    @AfterThrowing(pointcut = "webLayer() || serviceLayer() || persistenceLayer()", throwing = "exception")
    public void logException(final JoinPoint joinPoint, final Throwable exception) {
        if (!currentRequestContext.isExceptionLogged(exception)) {
            componentLogger(joinPoint)
                .error(
                    "Request-ID: {}",
                    currentRequestContext.getRequestId(),
                    exception
                );
            currentRequestContext.setExceptionBeenLogged(exception);
        }
    }

    @Pointcut(
        value = "execution(* dev.arhor.simple.todo.web.controller..*(..))" +
            " && within(@org.springframework.web.bind.annotation.RestController *)"
    )
    private void webLayer() { /* no-op */ }

    @Pointcut(
        value = "execution(* dev.arhor.simple.todo.service..*(..))" +
            " && within(@org.springframework.stereotype.Service *)"
    )
    private void serviceLayer() { /* no-op */ }

    @Pointcut(
        value = "execution(* dev.arhor.simple.todo.data..*(..))" +
            " && within(@org.springframework.stereotype.Repository *)"
    )
    private void persistenceLayer() { /* no-op */ }

    private Logger componentLogger(final JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }
}
