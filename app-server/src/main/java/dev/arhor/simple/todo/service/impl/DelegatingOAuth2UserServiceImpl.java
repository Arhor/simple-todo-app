package dev.arhor.simple.todo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import dev.arhor.simple.todo.service.event.UserLoadedEvent;

@Service
public class DelegatingOAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public DelegatingOAuth2UserServiceImpl(final ApplicationEventPublisher applicationEventPublisher) {
        this(new DefaultOAuth2UserService(), applicationEventPublisher);
    }

    public DelegatingOAuth2UserServiceImpl(
        final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate,
        final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.delegate = delegate;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        var user = delegate.loadUser(userRequest);
        applicationEventPublisher.publishEvent(new UserLoadedEvent(user));
        return user;
    }
}
