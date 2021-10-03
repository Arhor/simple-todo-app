package dev.arhor.simple.todo;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.arhor.simple.todo.task.StartupTask;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class ToDoAppRunner implements ApplicationRunner {

    private final List<StartupTask> startupTasks;

    public static void main(String[] args) {
        SpringApplication.run(ToDoAppRunner.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        startupTasks.stream().sorted().forEach(StartupTask::execute);
    }
}
