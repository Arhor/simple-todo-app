package dev.arhor.simple.todo.infrastructure.startup;

import dev.arhor.simple.todo.common.Result;

@FunctionalInterface
public interface Verifier {

    Result<String> verify();
}
