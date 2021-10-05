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

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messages;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleDefault(Exception exception, Locale locale, TimeZone timeZone) {
        log.error(exception.getMessage(), exception);

        final var message = (exception instanceof LocalizedException localizedException)
            ? messages.getMessage(localizedException.getLabel(), localizedException.getParams(), locale)
            : exception.getMessage();

        final var timestamp = Instant.now(Clock.system(
            (timeZone != null)
                ? timeZone.toZoneId()
                : ZoneId.systemDefault()));

        return Map.of("message", message, "timestamp", timestamp);
    }

}
