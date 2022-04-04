package dev.arhor.simple.todo.web.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.arhor.simple.todo.service.dto.ToDoItemDto;

@RequestMapping("/todos")
public interface ToDoItemController {

    @GetMapping
    @PreAuthorize("isAuthenticated() and hasRole('USER')")
    List<ToDoItemDto> getToDoItems(Authentication auth);

    @PostMapping
    @PreAuthorize("isAuthenticated() and hasRole('USER')")
    ToDoItemDto createToDoItem(ToDoItemDto toDoItem, Authentication auth);

    @PatchMapping
    @PreAuthorize("isAuthenticated() and hasRole('USER')")
    ToDoItemDto updateToDoItem(ToDoItemDto toDoItem, Authentication auth);

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() and hasRole('USER')")
    void deleteToDoItem(@PathVariable Long id, Authentication auth);
}
