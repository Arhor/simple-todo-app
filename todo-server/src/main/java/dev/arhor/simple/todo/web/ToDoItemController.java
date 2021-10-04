package dev.arhor.simple.todo.web;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.arhor.simple.todo.service.ToDoItemDto;
import dev.arhor.simple.todo.service.ToDoItemService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class ToDoItemController {

    private final ToDoItemService service;

    @GetMapping
    @PreAuthorize("isAuthenticated() and hasRole('USER')")
    public List<ToDoItemDto> getToDoItems(Authentication auth) {
        var owner = determineOwner(auth);
        return service.getToDoItemsByOwner(owner);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated() and hasRole('USER')")
    public ToDoItemDto createToDoItem(ToDoItemDto toDoItem, Authentication auth) {
        var owner = determineOwner(auth);
        return service.createToDoItem(toDoItem, owner);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() and hasRole('USER')")
    public void deleteToDoItem(@PathVariable Long id, Authentication auth) {
        var owner = determineOwner(auth);
        service.removeToDoItemById(id, owner);
    }

    private String determineOwner(Authentication auth) {
        if (auth instanceof OAuth2AuthenticationToken oAuth2Token) {
            var registrationId = oAuth2Token.getAuthorizedClientRegistrationId();
            var name = oAuth2Token.getName();
            return registrationId + ":" + name;
        }
        return auth.getName();
    }
}
