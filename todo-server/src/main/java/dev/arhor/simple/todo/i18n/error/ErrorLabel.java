package dev.arhor.simple.todo.i18n.error;

import dev.arhor.simple.todo.i18n.Label;

public enum ErrorLabel implements Label {
    // @formatter:off
    ERROR_SERVER_INTERNAL          ("error.server.internal"),
    ERROR_SERVER_HANDLER_NOT_FOUND ("error.server.handler.not.found"),
    ERROR_ENTITY_NOT_FOUND         ("error.entity.not.found"),
    ERROR_ENTITY_DUPLICATE         ("error.entity.duplicate"),
    ERROR_ENTITY_VALIDATION_FAILED ("error.entity.validation.failed"),
    ERROR_VALUE_TYPE_MISMATCH      ("error.value.type.mismatch"),
    // @formatter:on
    ;

    private final String code;

    ErrorLabel(final String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}
