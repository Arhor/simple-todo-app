package dev.arhor.simple.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate;
    @Mock private OAuth2User expectedUser;
    @Mock private OAuth2UserRequest userRequest;

    @InjectMocks
    private UserService userService;

    @Test
    void setDelegate() {
        // given
        when(delegate.loadUser(userRequest)).thenReturn(expectedUser);

        // when
        var actualUser = userService.loadUser(userRequest);

        // then
        assertThat(actualUser).isEqualTo(expectedUser);
    }
}