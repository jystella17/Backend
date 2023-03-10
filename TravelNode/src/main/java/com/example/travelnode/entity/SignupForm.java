package com.example.travelnode.entity;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
public class SignupForm {
    @NotBlank(message = "이름")
    private String name;
    @NotBlank(message = "이메일 주소")
    @Email(message = "올바른 형식의 이메일 주소")
    private String email;
    @NotBlank(message = "닉네임")
    private String nickname;

    public SignupForm() {
    }

    public User toEntity(String encPwd) {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .roleType(RoleType.USER)
                .providerType(ProviderType.KAKAO)
                .createdAt(LocalDateTime.now())
                .travelCount(0)
                .level(1)
                .build();
    }
}