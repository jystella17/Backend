package com.example.travelnode.oauth2.entity;

import com.example.travelnode.entity.ProviderType;
import com.example.travelnode.entity.RoleType;
import com.example.travelnode.entity.Token;
import com.example.travelnode.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
public class UserPrincipal implements OAuth2User, UserDetails {
    @Getter
    private final String uniqueId;
    @Getter
    private final String email;
    private final String nickname;
    @Getter
    private final RoleType roleType;
    @Getter
    private final ProviderType providerType;
    @Getter
    private final Collection<GrantedAuthority> authorities;
    @Getter
    private Map<String, Object> attributes;

    public UserPrincipal(String uniqueId, String email, String nickname, RoleType roleType,
                         ProviderType providerType, Collection<GrantedAuthority> authorities) {
        this.uniqueId = uniqueId;
        this.email = email;
        this.nickname = nickname;
        this.roleType = roleType;
        this.providerType = providerType;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(user.getRoleType().getCode()));

        return new UserPrincipal(
                user.getUniqueId(), user.getEmail(), user.getNickname(),
                user.getRoleType(), user.getProviderType(), authorities
        );
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);

        return userPrincipal;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() { return nickname; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() { return uniqueId; }
}


