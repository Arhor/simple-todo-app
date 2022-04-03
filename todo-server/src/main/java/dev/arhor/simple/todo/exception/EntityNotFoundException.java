package dev.arhor.simple.todo.exception;

import static dev.arhor.simple.todo.i18n.error.ErrorLabel.ERROR_ENTITY_NOT_FOUND;

public final class EntityNotFoundException extends PropertyConditionException {

    public EntityNotFoundException(final String name, final String condition) {
        super(ERROR_ENTITY_NOT_FOUND, name, condition);
    }
}
