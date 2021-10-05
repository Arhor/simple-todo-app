package dev.arhor.simple.todo;

import org.springframework.security.core.Authentication;

public interface OwnerResolver {

    String resolveOwnerId(Authentication authentication);
}
