package dev.arhor.simple.todo.service.event;

import org.springframework.security.core.AuthenticatedPrincipal;

public record UserLoadedEvent(AuthenticatedPrincipal principal) {
}
