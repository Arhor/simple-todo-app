package dev.arhor.simple.todo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.arhor.simple.todo.data.model.ToDoItem;
import dev.arhor.simple.todo.data.repository.ToDoItemRepository;
import dev.arhor.simple.todo.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ToDoItemServiceImpl implements ToDoItemService {

    private final ToDoItemRepository repository;
    private final ToDoItemConverter converter;
    private final StringSanitizer sanitizer;

    @Override
    public List<ToDoItemDto> getToDoItemsByOwner(String owner) {
        var toDoItems = repository.findAllByOwner(owner);
        return converter.batchConvertEntityToDto(toDoItems)
            .stream()
            .map(this::sanitize)
            .toList();
    }

    @Override
    public ToDoItemDto createToDoItem(ToDoItemDto item, String owner) {
        var sanitizedItem = sanitize(item);
        var toDoItem = converter.convertDtoToEntity(sanitizedItem, owner);
        var savedToDoItem = repository.save(toDoItem);
        return converter.convertEntityToDto(savedToDoItem);
    }

    @Override
    public ToDoItemDto updateToDoItem(ToDoItemDto item, String owner) {
        var toDoItem = getToDoItemEnsureOwnerHasAccess(item.id(), owner);
        var sanitizedItem = sanitize(item);

        toDoItem.setName(sanitizedItem.name());
        toDoItem.setDueDate(sanitizedItem.dueDate());
        toDoItem.setComplete(sanitizedItem.complete());

        var savedToDoItem = repository.save(toDoItem);
        return converter.convertEntityToDto(savedToDoItem);
    }

    @Override
    public void deleteToDoItemById(Long id, String owner) {
        var toDoItem = getToDoItemEnsureOwnerHasAccess(id, owner);
        repository.delete(toDoItem);
    }

    private ToDoItem getToDoItemEnsureOwnerHasAccess(Long id, String owner) {
        return repository.findById(id)
            .filter(toDoItem -> !toDoItem.getOwner().equals(owner))
            .orElseThrow(() -> new EntityNotFoundException("ToDoItem", "id", id));
    }

    private ToDoItemDto sanitize(ToDoItemDto dto) {
        var sanitizedName = sanitizer.sanitize(dto.name());
        return dto.copy().name(sanitizedName).build();
    }
}
