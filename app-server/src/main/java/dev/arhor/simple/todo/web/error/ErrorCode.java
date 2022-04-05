package dev.arhor.simple.todo.web.error;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ErrorCodeSerializer.class)
public enum ErrorCode {
    // @formatter:off
    UNCATEGORIZED            (Type.GEN, 0),

    VALIDATION_FAILED        (Type.VAL, 0),

    SECURITY_VIOLATION       (Type.SEC, 0),

    DATA_ACCESS_ERROR        (Type.DAT, 0),
    NOT_FOUND                (Type.DAT, 1),
    DUPLICATE                (Type.DAT, 2),

    HANDLER_NOT_FOUND        (Type.SRV, 0),
    METHOD_ARG_TYPE_MISMATCH (Type.SRV, 1),
    // @formatter:on
    ;

    private final Type type;
    private final int numericValue;

    ErrorCode(final Type type, final int numericValue) {
        this.type = type;
        this.numericValue = numericValue;
    }

    public Type getType() {
        return type;
    }

    public int getNumericValue() {
        return numericValue;
    }

    public enum Type {
        // @formatter:off
        GEN("GENERAL"),
        SEC("SECURITY"),
        VAL("VALIDATION"),
        DAT("DATA"),
        SRV("SERVER"),
        // @formatter:on
        ;

        private final String description;

        Type(final String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
