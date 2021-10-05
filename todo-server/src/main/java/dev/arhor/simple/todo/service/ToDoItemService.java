package dev.arhor.simple.todo.service;

import java.util.List;

public interface ToDoItemService {

    List<ToDoItemDto> getToDoItemsByOwner(String owner);

    ToDoItemDto createToDoItem(ToDoItemDto item, String owner);

    ToDoItemDto updateToDoItem(ToDoItemDto item, String owner);

    void deleteToDoItemById(Long id, String owner);
}
