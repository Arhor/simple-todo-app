package dev.arhor.simple.todo.service;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ToDoItemDto(
    @JsonProperty(access = READ_ONLY)
    Long id,
    String name,
    Instant dueDate,
    Boolean complete
) {
}
