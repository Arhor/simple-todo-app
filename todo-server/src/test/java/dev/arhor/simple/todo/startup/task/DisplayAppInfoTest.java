package dev.arhor.simple.todo.startup.task;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.mock;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;

import dev.arhor.simple.todo.infrastructure.startup.tasks.DisplayAppInfo;

class DisplayAppInfoTest {

    private DisplayAppInfo displayAppInfoTask;

    @BeforeEach
    void setUp() {
        var mockEnv = mock(Environment.class);
        displayAppInfoTask = new DisplayAppInfo(mockEnv);
    }

    @Test
    void execute_withoutExceptions() {
        // given
        ThrowingCallable action = displayAppInfoTask::execute;

        // then
        assertThatNoException().isThrownBy(action);
    }
}
