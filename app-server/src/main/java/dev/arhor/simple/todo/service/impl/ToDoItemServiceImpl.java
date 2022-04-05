package dev.arhor.simple.todo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.arhor.simple.todo.data.model.ToDoItem;
import dev.arhor.simple.todo.data.repository.ToDoItemRepository;
import dev.arhor.simple.todo.exception.EntityNotFoundException;
import dev.arhor.simple.todo.service.StringSanitizer;
import dev.arhor.simple.todo.service.TimeService;
import dev.arhor.simple.todo.service.ToDoItemConverter;
import dev.arhor.simple.todo.service.ToDoItemService;
import dev.arhor.simple.todo.service.dto.ToDoItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
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
    @Transactional
    public ToDoItemDto createToDoItem(final ToDoItemDto item, final String owner) {
        var sanitizedItem = sanitize(item);
        var toDoItem = toDoItemConverter.convertDtoToEntity(sanitizedItem, owner);
        var savedToDoItem = toDoItemRepository.save(toDoItem);
        return toDoItemConverter.convertEntityToDto(savedToDoItem);
    }

    @Override
    @Transactional
    public ToDoItemDto updateToDoItem(final ToDoItemDto item, final String owner) {
        var toDoItem = getToDoItemEnsureOwnerHasAccess(item.id(), owner);
        var sanitizedItem = sanitize(item);

        toDoItem.setName(sanitizedItem.name());
        toDoItem.setDueDate(sanitizedItem.dueDate());
        toDoItem.setComplete(sanitizedItem.complete());

        var savedToDoItem = toDoItemRepository.save(toDoItem);
        return toDoItemConverter.convertEntityToDto(savedToDoItem);
    }

    @Override
    @Transactional
    public void deleteToDoItemById(final Long id, final String owner) {
        var toDoItem = getToDoItemEnsureOwnerHasAccess(id, owner);
        toDoItemRepository.delete(toDoItem);
    }

    @Override
    @Transactional
    public void deleteOverdueToDoItems() {
        var dateWeekAgo = timeService.weekAgo();
        log.info("Deleting overdue todo items with 'dueDate' before {}", dateWeekAgo);
        var deletedItems = toDoItemRepository.deleteToDoItemsByDueDateBefore(dateWeekAgo);
        log.info("Deleted {} todo items", deletedItems);
    }

    private ToDoItem getToDoItemEnsureOwnerHasAccess(final Long id, final String owner) {
        return toDoItemRepository.findById(id)
            .filter(toDoItem -> !toDoItem.getOwner().equals(owner))
            .orElseThrow(() -> new EntityNotFoundException("ToDoItem", "id=" + id));
    }

    private ToDoItemDto sanitize(final ToDoItemDto dto) {
        var sanitizedName = sanitizer.sanitize(dto.name());
        return dto.copy().name(sanitizedName).build();
    }
}
