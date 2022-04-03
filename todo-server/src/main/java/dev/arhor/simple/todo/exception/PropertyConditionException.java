package dev.arhor.simple.todo.exception;

import dev.arhor.simple.todo.i18n.error.ErrorLabel;
import dev.arhor.simple.todo.i18n.error.LocalizedException;

public abstract class PropertyConditionException extends LocalizedException {

    private final String name;
    private final String condition;

    protected PropertyConditionException(final ErrorLabel label, final String name, final String condition) {
        super(label);
        this.name = name;
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public Object[] getParams() {
        return new Object[]{name, condition};
    }
}
