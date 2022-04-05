package dev.arhor.simple.todo.web.error;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ErrorCodeSerializer extends StdSerializer<ErrorCode> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final int NUM_CODE_MAX_LENGTH = 5;
    private static final char NUM_CODE_PAD_SYMBOL = '0';

    public ErrorCodeSerializer() {
        super(ErrorCode.class);
    }

    @Override
    public void serialize(final ErrorCode value, final JsonGenerator generator, final SerializerProvider provider)
        throws IOException {

        var type = value.getType().name();
        var code = convertCodeToPaddedString(value);

        generator.writeString(type + "-" + code);
    }

    private String convertCodeToPaddedString(final ErrorCode value) {
        var numberAsString = String.valueOf(value.getNumericValue());

        if (numberAsString.length() > NUM_CODE_MAX_LENGTH) {
            log.debug("ErrorCode {} numeric value is too large", value);
        }
        return StringUtils.leftPad(numberAsString, NUM_CODE_MAX_LENGTH, NUM_CODE_PAD_SYMBOL);
    }
}
