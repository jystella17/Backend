package com.example.travelnode.route;

import java.time.LocalDateTime;
import java.util.*;

import com.example.travelnode.entity.ProviderType;
import com.example.travelnode.entity.RoleType;
import com.example.travelnode.entity.User;
import com.example.travelnode.oauth2.entity.UserPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class WithMockCustomOAuth2AccountSecurityContextFactory
        implements WithSecurityContextFactory<WithMockCustomOAuth2Account> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomOAuth2Account mockCustomOAuth2Account) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(RoleType.USER.getCode()));

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

        UserPrincipal userPrincipal = new UserPrincipal(mockCustomOAuth2Account.id(),
                mockCustomOAuth2Account.email(), mockCustomOAuth2Account.nickname(),
                RoleType.USER, ProviderType.KAKAO, authorities);

        securityContext.setAuthentication(token);
        return securityContext;
    }
}