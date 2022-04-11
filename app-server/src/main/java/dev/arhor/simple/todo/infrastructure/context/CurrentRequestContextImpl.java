package dev.arhor.simple.todo.infrastructure.context;

import static org.springframework.context.annotation.ScopedProxyMode.INTERFACES;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = INTERFACES)
public class CurrentRequestContextImpl implements CurrentRequestContext {

    private UUID requestId = UUID.randomUUID();
    private Set<Throwable> loggedExceptions;

    @Override
    public UUID getRequestId() {
        return requestId;
    }

    @Override
    public void setRequestId(final UUID requestId) {
        this.requestId = requestId;
    }

    @Override
    public boolean isExceptionLogged(final Throwable throwable) {
        return (loggedExceptions != null) && loggedExceptions.contains(throwable);
    }

    @Override
    public void setExceptionBeenLogged(final Throwable throwable) {
        if (loggedExceptions == null) {
            loggedExceptions = new HashSet<>();
        }
        loggedExceptions.add(throwable);
    }
}
