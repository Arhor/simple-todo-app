package dev.arhor.simple.todo.exception;

import static dev.arhor.simple.todo.i18n.error.ErrorLabel.ERROR_ENTITY_DUPLICATE;

public final class EntityDuplicateException extends PropertyConditionException {

    public EntityDuplicateException(final String name, final String condition) {
        super(ERROR_ENTITY_DUPLICATE, name, condition);
    }
}
