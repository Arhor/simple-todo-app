package dev.arhor.simple.todo.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.arhor.simple.todo.service.ToDoItemService;
import dev.arhor.simple.todo.service.dto.ToDoItemDto;
import dev.arhor.simple.todo.web.security.OwnerResolver;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ToDoItemController {

    private final ToDoItemService service;
    private final OwnerResolver ownerResolver;

    @GetMapping
    @PreAuthorize("isAuthenticated() and hasRole('USER')")
    public List<ToDoItemDto> getToDoItems(final Authentication auth) {
        var ownerId = ownerResolver.resolveOwnerId(auth);
        return service.getToDoItemsByOwner(ownerId);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated() and hasRole('USER')")
    public ToDoItemDto createToDoItem(final ToDoItemDto toDoItem, final Authentication auth) {
        var ownerId = ownerResolver.resolveOwnerId(auth);
        return service.createToDoItem(toDoItem, ownerId);
    }

    @PatchMapping
    @PreAuthorize("isAuthenticated() and hasRole('USER')")
    public ToDoItemDto updateToDoItem(final ToDoItemDto toDoItem, final Authentication auth) {
        var ownerId = ownerResolver.resolveOwnerId(auth);
        return service.updateToDoItem(toDoItem, ownerId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() and hasRole('USER')")
    public void deleteToDoItem(@PathVariable final Long id, final Authentication auth) {
        var owner = ownerResolver.resolveOwnerId(auth);
        service.deleteToDoItemById(id, owner);
    }
}
