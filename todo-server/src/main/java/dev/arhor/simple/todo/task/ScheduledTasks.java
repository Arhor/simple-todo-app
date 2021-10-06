package dev.arhor.simple.todo.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dev.arhor.simple.todo.data.repository.ToDoItemRepository;
import dev.arhor.simple.todo.service.TimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final ToDoItemRepository toDoItemRepository;
    private final TimeService timeService;

    @Transactional
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void deleteOverdueToDoItems() {
        var dateWeekAgo = timeService.daysBeforeNow(7);
        log.info("Deleting overdue todo items with 'dueDate' before {} UTC", dateWeekAgo);
        var deletedItems = toDoItemRepository.deleteToDoItemsByDueDateBefore(dateWeekAgo);
        log.info("Deleted {} todo items", deletedItems);
    }
}
