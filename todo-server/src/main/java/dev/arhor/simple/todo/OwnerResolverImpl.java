package dev.arhor.simple.todo;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class OwnerResolverImpl implements OwnerResolver {

    private static final String SEPARATOR = ":";

    @Override
    public String resolveOwnerId(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken oAuth2Token) {
            return resolveOwnerIdFromOAuth2Token(oAuth2Token);
        }
        return authentication.getName();
    }

    private String resolveOwnerIdFromOAuth2Token(OAuth2AuthenticationToken oAuth2Token) {
        var registrationId = oAuth2Token.getAuthorizedClientRegistrationId();
        var name = oAuth2Token.getName();
        return registrationId + SEPARATOR + name;
    }
}
