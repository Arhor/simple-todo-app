package dev.arhor.simple.todo.exception;

public abstract class PropertyConditionException extends RuntimeException {

    private final String name;
    private final String condition;

    protected PropertyConditionException(final String name, final String condition) {
        this.name = name;
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public String getCondition() {
        return condition;
    }

    public Object[] getParams() {
        return new Object[]{name, condition};
    }
}
