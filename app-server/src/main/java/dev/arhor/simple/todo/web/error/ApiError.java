package dev.arhor.simple.todo.web.error;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import dev.arhor.simple.todo.exception.ErrorCode;

@JsonPropertyOrder({
    "code",
    "message",
    "details",
    "timestamp",
    "requestId",
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
    UUID requestId,
    String message,
    ErrorCode code,
    List<String> details,
    Instant timestamp
) implements Serializable {

    public static Builder builder() {
        return new Builder();
    }

    @JsonGetter
    public UUID getRequestId() {
        return requestId;
    }

    @JsonGetter
    public String getMessage() {
        return message;
    }

    @JsonGetter
    public ErrorCode getCode() {
        return code;
    }

    @JsonGetter
    public Instant getTimestamp() {
        return timestamp;
    }

    @JsonGetter
    public List<String> getDetails() {
        return details;
    }

    public static final class Builder {

        private UUID requestId;
        private String message;
        private ErrorCode code = ErrorCode.UNCATEGORIZED;
        private List<String> details;
        private Instant timestamp;

        public ApiError build() {
            return new ApiError(requestId, message, code, details, timestamp);
        }

        public Builder requestId(final UUID requestId) {
            this.requestId = requestId;
            return this;
        }

        public Builder message(final String message) {
            this.message = message;
            return this;
        }

        public Builder code(final ErrorCode code) {
            this.code = code;
            return this;
        }

        public Builder details(final List<String> details) {
            this.details = details;
            return this;
        }

        public Builder timestamp(final Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }
    }
}
