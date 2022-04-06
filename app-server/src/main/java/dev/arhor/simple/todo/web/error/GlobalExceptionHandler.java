package dev.arhor.simple.todo.web.error;

import static dev.arhor.simple.todo.config.LocalizationConfig.ERROR_MESSAGES_BEAN;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.Function;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import dev.arhor.simple.todo.exception.EntityDuplicateException;
import dev.arhor.simple.todo.exception.EntityNotFoundException;
import dev.arhor.simple.todo.infrastructure.context.CurrentRequestContext;
import dev.arhor.simple.todo.service.TimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GlobalExceptionHandler {

    @Qualifier(ERROR_MESSAGES_BEAN)
    private final MessageSource messages;
    private final TimeService timeService;
    private final CurrentRequestContext currentRequestContext;

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiError handleDefault(
        final Exception exception,
        final Locale locale,
        final TimeZone timeZone
    ) {
        logExceptionWithRequestId(exception, "Unhandled exception. Consider appropriate exception handler");
        return handleErrorCode(ErrorCode.UNCATEGORIZED, locale, timeZone);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ApiError handleEntityNotFoundException(
        final EntityNotFoundException exception,
        final Locale locale,
        final TimeZone timeZone
    ) {
        logExceptionWithRequestId(exception);
        return handleErrorCode(ErrorCode.NOT_FOUND, locale, timeZone, exception.getParams());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntityDuplicateException.class)
    public ApiError handleEntityDuplicateException(
        final EntityDuplicateException exception,
        final Locale locale,
        final TimeZone timeZone
    ) {
        logExceptionWithRequestId(exception);
        return handleErrorCode(ErrorCode.DUPLICATE, locale, timeZone, exception.getParams());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiError handleMethodArgumentTypeMismatchException(
        final MethodArgumentTypeMismatchException exception,
        final Locale locale,
        final TimeZone timeZone
    ) {
        logExceptionWithRequestId(exception);
        return handleErrorCode(
            ErrorCode.METHOD_ARG_TYPE_MISMATCH,
            locale,
            timeZone,
            exception.getName(),
            exception.getValue(),
            exception.getRequiredType()
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiError handleNoHandlerFoundException(
        final NoHandlerFoundException exception,
        final Locale locale,
        final TimeZone timeZone
    ) {
        logExceptionWithRequestId(exception);
        return handleErrorCode(
            ErrorCode.HANDLER_NOT_FOUND,
            locale,
            timeZone,
            exception.getHttpMethod(),
            exception.getRequestURL()
        );
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public ApiError handleAccessDeniedException(
        final AccessDeniedException exception,
        final Locale locale,
        final TimeZone timeZone
    ) {
        logExceptionWithRequestId(exception);
        return handleErrorCode(ErrorCode.UNAUTHORIZED, locale, timeZone);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handleMethodArgumentNotValidException(
        final MethodArgumentNotValidException exception,
        final Locale locale,
        final TimeZone timeZone
    ) {
        logExceptionWithRequestId(exception);

        var bindingResult = exception.getBindingResult();

        var errors = ListUtils.union(
            handleObjectErrors(
                bindingResult.getFieldErrors(),
                FieldError::getField,
                FieldError::getDefaultMessage
            ),
            handleObjectErrors(
                bindingResult.getGlobalErrors(),
                ObjectError::getObjectName,
                ObjectError::getDefaultMessage
            )
        );

        return handleErrorCode(ErrorCode.VALIDATION_FAILED, locale, timeZone, errors);
    }

    private void logExceptionWithRequestId(final Exception exception) {
        log.error("Request-ID: {}", currentRequestContext.getRequestId(), exception);
    }

    private void logExceptionWithRequestId(final Exception exception, final String message) {
        log.error("{}, Request-ID: {}", message, currentRequestContext.getRequestId(), exception);
    }

    private ApiError handleErrorCode(
        final ErrorCode error,
        final Locale locale,
        final TimeZone timeZone,
        final Object... args
    ) {
        return handleErrorCode(error, locale, timeZone, null, args);
    }

    private ApiError handleErrorCode(
        final ErrorCode error,
        final Locale locale,
        final TimeZone timeZone,
        final List<String> details,
        final Object... args
    ) {
        var requestId = currentRequestContext.getRequestId();
        var timestamp = timeService.now(timeZone);
        var message = messages.getMessage(error.getLabel(), args, locale);

        return ApiError.builder()
            .requestId(requestId)
            .timestamp(timestamp)
            .message(message)
            .code(error)
            .details(details)
            .build();
    }

    private <T extends ObjectError> List<String> handleObjectErrors(
        final List<? extends T> errors,
        final Function<? super T, String> nameProvider,
        final Function<? super T, String> messageProvider
    ) {
        var result = new ArrayList<String>(errors.size());
        for (var error : errors) {
            var name = nameProvider.apply(error);
            var message = messageProvider.apply(error);

            result.add(name + ": " + message);
        }
        return result;
    }
}
