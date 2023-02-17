package com.example.travelnode.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RoleType {
    USER("ROLE_USER", "회원"),
    ADMIN("ROLE_ADMIN", "관리자"),
    GUEST("ROLE_GUEST", "비회원");

    private final String key;
    private final String displayName;


    public static RoleType of(String key) {
        return Arrays.stream(RoleType.values())
                .filter(r -> r.getKey().equals(key))
                .findAny()
                .orElse(GUEST);
    }
}

