package dev.arhor.simple.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.arhor.simple.todo.data.model.ToDoItem;
import dev.arhor.simple.todo.data.repository.ToDoItemRepository;
import dev.arhor.simple.todo.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ToDoItemServiceImpl implements ToDoItemService {

    private final ToDoItemRepository toDoItemRepository;
    private final ToDoItemConverter toDoItemConverter;
    private final TimeService timeService;
    private final StringSanitizer sanitizer;

    @Override
    public List<ToDoItemDto> getToDoItemsByOwner(final String owner) {
        var toDoItems = toDoItemRepository.findAllByOwner(owner);
        return toDoItemConverter.batchConvertEntityToDto(toDoItems)
            .stream()
            .map(this::sanitize)
            .toList();
    }

    @Override
    public ToDoItemDto createToDoItem(ToDoItemDto item, String owner) {
        var sanitizedItem = sanitize(item);
        var toDoItem = toDoItemConverter.convertDtoToEntity(sanitizedItem, owner);
        var savedToDoItem = toDoItemRepository.save(toDoItem);
        return toDoItemConverter.convertEntityToDto(savedToDoItem);
    }

    @Override
    public ToDoItemDto updateToDoItem(ToDoItemDto item, String owner) {
        var toDoItem = getToDoItemEnsureOwnerHasAccess(item.id(), owner);
        var sanitizedItem = sanitize(item);

        toDoItem.setName(sanitizedItem.name());
        toDoItem.setDueDate(sanitizedItem.dueDate());
        toDoItem.setComplete(sanitizedItem.complete());

        var savedToDoItem = toDoItemRepository.save(toDoItem);
        return toDoItemConverter.convertEntityToDto(savedToDoItem);
    }

    @Override
    public void deleteToDoItemById(Long id, String owner) {
        var toDoItem = getToDoItemEnsureOwnerHasAccess(id, owner);
        toDoItemRepository.delete(toDoItem);
    }

    @Override
    public void deleteOverdueToDoItems() {
        var dateWeekAgo = timeService.weekAgo();
        log.info("Deleting overdue todo items with 'dueDate' before {}", dateWeekAgo);
        var deletedItems = toDoItemRepository.deleteToDoItemsByDueDateBefore(dateWeekAgo);
        log.info("Deleted {} todo items", deletedItems);
    }

    private ToDoItem getToDoItemEnsureOwnerHasAccess(Long id, String owner) {
        return toDoItemRepository.findById(id)
            .filter(toDoItem -> !toDoItem.getOwner().equals(owner))
            .orElseThrow(() -> new EntityNotFoundException("ToDoItem", "id=" + id));
    }

    private ToDoItemDto sanitize(ToDoItemDto dto) {
        var sanitizedName = sanitizer.sanitize(dto.name());
        return dto.copy().name(sanitizedName).build();
    }
}
