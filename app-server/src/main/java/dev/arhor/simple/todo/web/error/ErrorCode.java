package dev.arhor.simple.todo.web.error;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ErrorCodeSerializer.class)
public enum ErrorCode {
    UNCATEGORIZED            (Type.GEN, 0, "error.server.internal"),

    VALIDATION_FAILED        (Type.VAL, 0, "error.entity.validation.failed"),

    UNAUTHORIZED             (Type.SEC, 0, "error.server.unauthorized"),

    NOT_FOUND                (Type.DAT, 0, "error.entity.not.found"),
    DUPLICATE                (Type.DAT, 1, "error.entity.duplicate"),

    HANDLER_NOT_FOUND        (Type.SRV, 0, "error.server.handler.not.found"),
    METHOD_ARG_TYPE_MISMATCH (Type.SRV, 1, "error.value.type.mismatch"),

    ;

    private final Type type;
    private final int numericValue;
    private final String label;

    ErrorCode(final Type type, final int numericValue, final String label) {
        this.type = type;
        this.numericValue = numericValue;
        this.label = label;
    }

    public Type getType() {
        return type;
    }

    public int getNumericValue() {
        return numericValue;
    }

    public String getLabel() {
        return label;
    }

    public enum Type {
        GEN("GENERAL"),
        SEC("SECURITY"),
        VAL("VALIDATION"),
        DAT("DATA"),
        SRV("SERVER"),

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
