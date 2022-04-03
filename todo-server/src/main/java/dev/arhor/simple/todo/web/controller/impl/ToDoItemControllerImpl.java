package dev.arhor.simple.todo.web.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import dev.arhor.simple.todo.service.ToDoItemDto;
import dev.arhor.simple.todo.service.ToDoItemService;
import dev.arhor.simple.todo.web.controller.ToDoItemController;
import dev.arhor.simple.todo.web.security.OwnerResolver;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ToDoItemControllerImpl implements ToDoItemController {

    private final ToDoItemService service;
    private final OwnerResolver ownerResolver;

    public List<ToDoItemDto> getToDoItems(final Authentication auth) {
        var ownerId = ownerResolver.resolveOwnerId(auth);
        return service.getToDoItemsByOwner(ownerId);
    }

    public ToDoItemDto createToDoItem(final ToDoItemDto toDoItem, final Authentication auth) {
        var ownerId = ownerResolver.resolveOwnerId(auth);
        return service.createToDoItem(toDoItem, ownerId);
    }

    public ToDoItemDto updateToDoItem(final ToDoItemDto toDoItem, final Authentication auth) {
        var ownerId = ownerResolver.resolveOwnerId(auth);
        return service.updateToDoItem(toDoItem, ownerId);
    }

    public void deleteToDoItem(final Long id, final Authentication auth) {
        var owner = ownerResolver.resolveOwnerId(auth);
        service.deleteToDoItemById(id, owner);
    }
}
