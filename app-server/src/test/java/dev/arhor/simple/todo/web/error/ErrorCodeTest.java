package dev.arhor.simple.todo.web.error;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class ErrorCodeTest {

    @EnumSource(ErrorCode.Type.class)
    @ParameterizedTest(name = "should not have duplicate numeric values within {arguments} type")
    void errorCodes(final ErrorCode.Type type) {
        // when
        var errorCodesByType = Stream.of(ErrorCode.values()).filter(it -> it.getType() == type).toList();

        // then
        assertThat(errorCodesByType)
            .extracting(ErrorCode::getNumericValue)
            .doesNotHaveDuplicates();
    }
}
