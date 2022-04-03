package dev.arhor.simple.todo.service.schedule;

import static dev.arhor.simple.todo.common.TimeUtils.HOUR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dev.arhor.simple.todo.service.ToDoItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ScheduledTasks {

    private final ToDoItemService toDoItemService;

    @Scheduled(fixedRate = HOUR, initialDelay = HOUR)
    public void deleteOverdueToDoItems() {
        toDoItemService.deleteOverdueToDoItems();
    }
}
