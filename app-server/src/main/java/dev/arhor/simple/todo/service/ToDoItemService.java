package dev.arhor.simple.todo.service;

import java.util.List;

import dev.arhor.simple.todo.service.dto.ToDoItemDto;

public interface ToDoItemService {

    List<ToDoItemDto> getToDoItemsByOwner(String owner);

    ToDoItemDto createToDoItem(ToDoItemDto item, String owner);

    ToDoItemDto updateToDoItem(ToDoItemDto item, String owner);

    void deleteToDoItemById(Long id, String owner);

    void deleteOverdueToDoItems();
}
