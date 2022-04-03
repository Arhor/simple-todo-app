package dev.arhor.simple.todo.infrastructure.context;

import java.util.UUID;

public interface CurrentRequestContext {

    UUID getRequestId();
}
