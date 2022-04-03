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
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import dev.arhor.simple.todo.infrastructure.context.CurrentRequestContext;
import dev.arhor.simple.todo.exception.EntityDuplicateException;
import dev.arhor.simple.todo.exception.EntityNotFoundException;
import dev.arhor.simple.todo.exception.ErrorCode;
import dev.arhor.simple.todo.i18n.error.ErrorLabel;
import dev.arhor.simple.todo.i18n.error.LocalizedException;
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

        return ApiError.builder()
            .requestId(currentRequestContext.getRequestId())
            .timestamp(timeService.now(timeZone))
            .message(
                findTranslation(
                    locale,
                    ErrorLabel.ERROR_SERVER_INTERNAL
                )
            )
            .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ApiError handleEntityNotFoundException(
        final EntityNotFoundException exception,
        final Locale locale,
        final TimeZone timeZone
    ) {
        logExceptionWithRequestId(exception);

        return ApiError.builder()
            .requestId(currentRequestContext.getRequestId())
            .timestamp(timeService.now(timeZone))
            .message(findTranslationForException(locale, exception))
            .code(ErrorCode.NOT_FOUND)
            .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntityDuplicateException.class)
    public ApiError handleEntityDuplicateException(
        final EntityDuplicateException exception,
        final Locale locale,
        final TimeZone timeZone
    ) {
        logExceptionWithRequestId(exception);

        return ApiError.builder()
            .requestId(currentRequestContext.getRequestId())
            .timestamp(timeService.now(timeZone))
            .message(findTranslationForException(locale, exception))
            .code(ErrorCode.DUPLICATE)
            .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiError handleMethodArgumentTypeMismatchException(
        final MethodArgumentTypeMismatchException exception,
        final Locale locale,
        final TimeZone timeZone
    ) {
        logExceptionWithRequestId(exception);

        return ApiError.builder()
            .requestId(currentRequestContext.getRequestId())
            .timestamp(timeService.now(timeZone))
            .message(
                findTranslation(
                    locale,
                    ErrorLabel.ERROR_VALUE_TYPE_MISMATCH,
                    exception.getName(),
                    exception.getValue(),
                    exception.getRequiredType()
                )
            )
            .code(ErrorCode.METHOD_ARG_TYPE_MISMATCH)
            .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiError handleNoHandlerFoundException(
        final NoHandlerFoundException exception,
        final Locale locale,
        final TimeZone timeZone
    ) {
        logExceptionWithRequestId(exception);

        return ApiError.builder()
            .requestId(currentRequestContext.getRequestId())
            .timestamp(timeService.now(timeZone))
            .message(
                findTranslation(
                    locale,
                    ErrorLabel.ERROR_SERVER_HANDLER_NOT_FOUND,
                    exception.getHttpMethod(),
                    exception.getRequestURL()
                )
            )
            .code(ErrorCode.HANDLER_NOT_FOUND)
            .build();
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

        return ApiError.builder()
            .requestId(currentRequestContext.getRequestId())
            .timestamp(timeService.now(timeZone))
            .message(
                findTranslation(
                    locale,
                    ErrorLabel.ERROR_ENTITY_VALIDATION_FAILED
                )
            )
            .code(ErrorCode.VALIDATION_FAILED)
            .details(errors)
            .build();
    }

    private void logExceptionWithRequestId(final Exception exception) {
        log.error("Request-ID: {}", currentRequestContext.getRequestId(), exception);
    }

    private void logExceptionWithRequestId(final Exception exception, final String message) {
        log.error("{}, Request-ID: {}", message, currentRequestContext.getRequestId(), exception);
    }

    private String findTranslation(final Locale locale, final ErrorLabel label, final Object... params) {
        return messages.getMessage(
            label.getCode(),
            params,
            locale
        );
    }

    private String findTranslationForException(final Locale locale, final LocalizedException exception) {
        return findTranslation(
            locale,
            exception.getLabel(),
            exception.getParams()
        );
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
