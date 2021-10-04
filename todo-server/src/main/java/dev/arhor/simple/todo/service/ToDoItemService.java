package dev.arhor.simple.todo.service;

import java.util.List;

public interface ToDoItemService {

    List<ToDoItemDto> getToDoItemsByOwner(String owner);

    ToDoItemDto createToDoItem(ToDoItemDto item, String owner);

    List<ToDoItemDto> createToDoItems(List<ToDoItemDto> items, String owner);

    void removeToDoItemById(Long id, String owner);

    void removeToDoItemsByIds(List<Long> ids, String owner);
}
