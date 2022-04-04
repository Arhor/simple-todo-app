package dev.arhor.simple.todo.service.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserLoadedEventListener {

    @Async
    @EventListener(UserLoadedEvent.class)
    public void handleUserLoadedEvent(final UserLoadedEvent event) {
        log.debug("OAuth2User user loaded: {}", event.principal().getName());
    }
}
