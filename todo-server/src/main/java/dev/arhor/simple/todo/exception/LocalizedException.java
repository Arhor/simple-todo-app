package dev.arhor.simple.todo.exception;

public abstract class LocalizedException extends RuntimeException {

    protected LocalizedException() {
    }

    protected LocalizedException(String message) {
        super(message);
    }

    public abstract String getLabel();

    public abstract Object[] getParams();
}
