package dev.arhor.simple.todo.task;

import org.springframework.core.Ordered;

@FunctionalInterface
public interface StartupTask extends Ordered, Comparable<StartupTask> {

    int MEDIUM_PRECEDENCE = 0;

    @Override
    default int getOrder() {
        return MEDIUM_PRECEDENCE;
    }

    @Override
    default int compareTo(StartupTask other) {
        return this.getOrder() - other.getOrder();
    }

    void execute();
}
