package dev.arhor.simple.todo.web;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.arhor.simple.todo.OwnerResolver;
import dev.arhor.simple.todo.service.ToDoItemDto;
import dev.arhor.simple.todo.service.ToDoItemService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class ToDoItemController {

    private final ToDoItemService service;
    private final OwnerResolver ownerResolver;

    @GetMapping
    @PreAuthorize("isAuthenticated() and hasRole('USER')")
    public List<ToDoItemDto> getToDoItems(Authentication auth) {
        var owner = ownerResolver.resolveOwnerId(auth);
        return service.getToDoItemsByOwner(owner);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated() and hasRole('USER')")
    public ToDoItemDto createToDoItem(ToDoItemDto toDoItem, Authentication auth) {
        var owner = ownerResolver.resolveOwnerId(auth);
        return service.createToDoItem(toDoItem, owner);
    }

    @PatchMapping
    @PreAuthorize("isAuthenticated() and hasRole('USER')")
    public ToDoItemDto updateToDoItem(ToDoItemDto toDoItem, Authentication auth) {
        var owner = ownerResolver.resolveOwnerId(auth);
        return service.updateToDoItem(toDoItem, owner);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() and hasRole('USER')")
    public void deleteToDoItem(@PathVariable Long id, Authentication auth) {
        var owner = ownerResolver.resolveOwnerId(auth);
        service.deleteToDoItemById(id, owner);
    }
}
