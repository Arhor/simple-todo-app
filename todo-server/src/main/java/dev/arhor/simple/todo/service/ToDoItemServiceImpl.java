package dev.arhor.simple.todo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.arhor.simple.todo.data.model.ToDoItem;
import dev.arhor.simple.todo.data.repository.ToDoItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ToDoItemServiceImpl implements ToDoItemService {

    private final ToDoItemRepository repository;
    private final ToDoItemConverter converter;

    @Override
    public List<ToDoItemDto> getToDoItemsByOwner(String owner) {
        var toDoItems = repository.findAllByOwner(owner);
        return converter.batchConvertEntityToDto(toDoItems);
    }

    @Override
    public ToDoItemDto createToDoItem(ToDoItemDto item, String owner) {
        var toDoItem = converter.convertDtoToEntity(item);
        toDoItem.setOwner(owner);
        var savedToDoItem = repository.save(toDoItem);
        return converter.convertEntityToDto(savedToDoItem);
    }

    @Override
    public List<ToDoItemDto> createToDoItems(List<ToDoItemDto> items, String owner) {
        if (items.isEmpty()) {
            return items;
        }
        var toDoItems = converter.batchConvertDtoToEntity(items);
        for (ToDoItem toDoItem : toDoItems) {
            toDoItem.setOwner(owner);
        }
        var savedToDoItems = repository.saveAll(toDoItems);
        return converter.batchConvertEntityToDto(savedToDoItems);
    }

    @Override
    public void removeToDoItemById(Long id, String owner) {
        validate(id, owner);
        repository.deleteById(id);
    }

    @Override
    public void removeToDoItemsByIds(List<Long> ids, String owner) {
        for (Long id : ids) {
            validate(id, owner);
        }
        repository.deleteAllById(ids);
    }

    private void validate(Long id, String owner) {
        var toDoItem = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        if (!toDoItem.getOwner().equals(owner)) {
            throw new IllegalAccessAttemptException();
        }
    }

    public static class EntityNotFoundException extends RuntimeException {
    }

    public static class IllegalAccessAttemptException extends RuntimeException {
    }
}
