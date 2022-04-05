package dev.arhor.simple.todo.infrastructure.startup.tasks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import dev.arhor.simple.todo.common.Result.Failure;
import dev.arhor.simple.todo.common.Result.Success;
import dev.arhor.simple.todo.infrastructure.startup.StartupTask;
import dev.arhor.simple.todo.infrastructure.startup.Verifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class VerifyAppInitialState implements StartupTask {

    private final List<Verifier> verifiers;

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public void execute() {
        var succeed = verifiers.stream().map(Verifier::verify).allMatch(result -> switch (result) {
            case Success success -> {
                log.info("[SUCCESS]: {}", success.value());
                yield true;
            }
            case Failure failure -> {
                log.error("[FAILURE]: {}", failure.error().getMessage());
                yield false;
            }
        });
        if (!succeed) {
            throw new IllegalStateException("Application verification failed");
        }
    }
}
