package dev.arhor.simple.todo.i18n.error;

import static dev.arhor.simple.todo.config.LocalizationConfig.ERROR_MESSAGES_BEAN;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import dev.arhor.simple.todo.config.LocalizationConfig;

class ErrorLabelTest {

    @SpringJUnitConfig(LocalizationConfig.class)
    static class ErrorLabelParametrizedTest {

        @Autowired
        @Qualifier(ERROR_MESSAGES_BEAN)
        private MessageSource errorMessages;

        @EnumSource(ErrorLabel.class)
        @ParameterizedTest(name = "should have localization for {arguments} error code")
        void errorLabel(final ErrorLabel label) {
            // given
            var code = label.getCode();
            var args = new Object[0];
            var locale = LocalizationConfig.DEFAULT_LOCALE;

            // when
            var message = errorMessages.getMessage(code, args, locale);

            // then
            assertThat(message)
                .isNotBlank();
        }
    }

    @Test
    @DisplayName("should not use duplicating translation codes")
    void should_not_use_duplicating_translation_codes() {
        // given
        var translationCodes = ErrorLabel.values();

        // then
        assertThat(translationCodes)
            .extracting(ErrorLabel::getCode)
            .doesNotHaveDuplicates();
    }
}
