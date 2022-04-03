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

    public CopyBuilder copy() {
        return new CopyBuilder(id, name, dueDate, complete);
    }

    public static final class CopyBuilder {

        private Long id;
        private String name;
        private Instant dueDate;
        private Boolean complete;

        private CopyBuilder(final Long id, final String name, final Instant dueDate, final Boolean complete) {
            this.id = id;
            this.name = name;
            this.dueDate = dueDate;
            this.complete = complete;
        }

        public CopyBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public CopyBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public CopyBuilder dueDate(final Instant dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public CopyBuilder complete(final Boolean complete) {
            this.complete = complete;
            return this;
        }

        public ToDoItemDto build() {
            return new ToDoItemDto(id, name, dueDate, complete);
        }
    }
}
