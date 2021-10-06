package dev.arhor.simple.todo.web;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import dev.arhor.simple.todo.exception.LocalizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @see org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messages;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleDefault(Exception exception, Locale locale, TimeZone timeZone) {
        log.error(exception.getMessage(), exception);

        final var message = getMessage(exception, locale);
        final var timestamp = getTimestamp(timeZone);

        return Map.of("message", message, "timestamp", timestamp);
    }

    private String getMessage(Exception exception, Locale locale) {
        if (exception instanceof LocalizedException localizedException) {
            final var label = localizedException.getLabel();
            final var params = localizedException.getParams();
            return messages.getMessage(label, params, locale);
        }
        return exception.getMessage();
    }

    private Instant getTimestamp(TimeZone timeZone) {
        final var zoneId = (timeZone != null) ? timeZone.toZoneId() : ZoneId.systemDefault();
        final var clock = Clock.system(zoneId);
        return Instant.now(clock);
    }
}
