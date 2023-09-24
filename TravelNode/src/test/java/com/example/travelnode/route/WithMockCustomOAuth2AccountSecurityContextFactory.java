package com.example.travelnode.route;

import java.util.List;
import java.util.Map;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.HashMap;

public class WithMockCustomOAuth2AccountSecurityContextFactory
        implements WithSecurityContextFactory<WithMockCustomOAuth2Account> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomOAuth2Account mockCustomOAuth2Account) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", mockCustomOAuth2Account.id());
        attributes.put("email", mockCustomOAuth2Account.email());
        attributes.put("nickname", mockCustomOAuth2Account.nickname());

        OAuth2User oAuth2User = new DefaultOAuth2User(
                List.of(new OAuth2UserAuthority(mockCustomOAuth2Account.role().toString(), attributes)),
                attributes, "id");

        OAuth2AuthenticationToken token = new OAuth2AuthenticationToken(
                oAuth2User, oAuth2User.getAuthorities(),
                mockCustomOAuth2Account.provider().toString());

        securityContext.setAuthentication(token);
        return securityContext;
    }
}
