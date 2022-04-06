package dev.arhor.simple.todo.exception;

public final class EntityNotFoundException extends PropertyConditionException {

    public EntityNotFoundException(final String name, final String condition) {
        super(name, condition);
    }
}
