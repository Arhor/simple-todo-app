package dev.arhor.simple.todo.web.security;

import org.springframework.security.core.Authentication;

public interface OwnerResolver {

    String resolveOwnerId(Authentication authentication);
}
