package dev.arhor.simple.todo.service.impl;

import static java.util.Map.entry;

import java.util.Map;

import org.springframework.stereotype.Component;

import dev.arhor.simple.todo.service.StringSanitizer;

@Component
public class StringSanitizerImpl implements StringSanitizer {

    private static final Map<String, String> MAPPINGS = Map.ofEntries(
        entry("<", "&lt;"),
        entry(">", "&gt;"),
        entry("&", "&amp;"),
        entry("\"", "&quot;")
    );

    @Override
    public String sanitize(final String input) {
        if (input == null) {
            return null;
        }
        var result = input;
        for (Map.Entry<String, String> unsafeElementMapping : MAPPINGS.entrySet()) {
            var unsafeElement = unsafeElementMapping.getKey();
            var replacement = unsafeElementMapping.getValue();

            result = result.replace(unsafeElement, replacement);
        }
        return result;
    }
}
