package dev.arhor.simple.todo.infrastructure.context;

import java.util.UUID;

public interface CurrentRequestContext {

    UUID getRequestId();

    void setRequestId(UUID requestId);

    boolean isExceptionLogged(Throwable throwable);

    void setExceptionBeenLogged(Throwable throwable);
}
