package dev.arhor.simple.todo.infrastructure.startup.task;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.mock;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import dev.arhor.simple.todo.infrastructure.startup.tasks.DisplayAppInfo;

@ExtendWith(MockitoExtension.class)
class DisplayAppInfoTest {

    @Mock private Environment environment;

    @InjectMocks private DisplayAppInfo displayAppInfoTask;

    @Test
    void execute_withoutExceptions() {
        // given
        ThrowingCallable action = displayAppInfoTask::execute;

        // then
        assertThatNoException().isThrownBy(action);
    }
}
