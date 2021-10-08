package dev.arhor.simple.todo.service;

import static java.util.Map.entry;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class StringSanitizerImpl implements StringSanitizer {

    private static final Map<String, String> MAPPINGS = Map.ofEntries(
        entry("<" , "&lt;"),
        entry(">" , "&gt;"),
        entry("&" , "&amp;"),
        entry("\"", "&quot;")
    );

    @Override
    public String sanitize(String input) {
        if (input == null) {
            return null;
        }

        for (Map.Entry<String, String> unsafeElementMapping : MAPPINGS.entrySet()) {
            var unsafeElement = unsafeElementMapping.getKey();
            var replacement = unsafeElementMapping.getValue();

            input = input.replace(unsafeElement, replacement);
        }

        return input;
    }

    Map<String, String> mappings() {
        return Map.copyOf(MAPPINGS);
    }

    String[] unsafeElements() {
        return MAPPINGS.keySet().toArray(String[]::new);
    }
}
