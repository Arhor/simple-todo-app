package dev.arhor.simple.todo.web.error;

import static dev.arhor.simple.todo.config.LocalizationConfig.ERROR_MESSAGES_BEAN;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import dev.arhor.simple.todo.config.LocalizationConfig;

@SpringJUnitConfig(LocalizationConfig.class)
class ErrorCodeTest {

    @Autowired
    @Qualifier(ERROR_MESSAGES_BEAN)
    private MessageSource errorMessages;

    @EnumSource(ErrorCode.class)
    @ParameterizedTest(name = "should have localization for {arguments} error code")
    void each_error_code_label_should_be_translated(final ErrorCode code) {
        // given
        var label = code.getLabel();
        var args = new Object[0];
        var locale = Locale.ENGLISH;

        // when
        var message = errorMessages.getMessage(label, args, locale);

        // then
        assertThat(message)
            .isNotBlank();
    }

    @EnumSource(ErrorCode.Type.class)
    @ParameterizedTest(name = "should not have duplicate numeric values within {arguments} type")
    void each_error_code_type_should_not_have_numeric_value_duplicates(final ErrorCode.Type type) {
        // when
        var errorCodesByType = Stream.of(ErrorCode.values()).filter(it -> it.getType() == type).toList();

        // then
        assertThat(errorCodesByType)
            .extracting(ErrorCode::getNumericValue)
            .doesNotHaveDuplicates();
    }
}
