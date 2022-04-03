package dev.arhor.simple.todo.i18n.error;

public abstract class LocalizedException extends RuntimeException {

    private final ErrorLabel label;

    protected LocalizedException(final ErrorLabel label) {
        this.label = label;
    }

    public ErrorLabel getLabel() {
        return label;
    }

    public abstract Object[] getParams();
}
