package com.example.travelnode.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER")
public class User {

    @Id
    @Column(name = "UID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @NotNull
    @Size(max = 128)
    @Column(name = "EMAIL", length = 128)
    private String email;

    @NotNull
    @Size(max = 20)
    @Column(name = "USER_ID", length = 20)
    private String userId;

    @NotNull
    @Size(max = 10)
    @Column(name = "NICKNAME", length = 10)
    private String nickname;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "PROVIDER_TYPE", length = 20)
    private RoleType roleType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE_TYPE", length = 10)
    private ProviderType providerType;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER", length = 10)
    private GenderType gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "AGE", length = 10)
    private AgeType age;

    @NotNull
    @JsonProperty("profileImgUrl")
    @Column(name = "PROFILE_IMG_URL", length = 512)
    private String profileImgUrl;

    @JsonIgnore
    @Column(name = "PROFILE_IMG_NAME", length = 512)
    private String profileImgName;

    @NotNull
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    public User(
            @NotNull @Size(max = 128) String email,
            @NotNull @Size(max = 20) String userId,
            @NotNull @Size(max = 10) String nickname,
            @NotNull RoleType roleType,
            @NotNull ProviderType providerType,
            GenderType gender,
            AgeType age,
            String profileImgUrl,
            @NotNull LocalDateTime createdAt,
            @NotNull LocalDateTime modifiedAt
    ) {
        this.email = email != null ? email : "NO_EMAIL";
        this.userId = userId;
        this.nickname = nickname;
        this.roleType = roleType;
        this.providerType = providerType;
        this.gender = gender != null ? gender : GenderType.UNDEFINED;
        this.age = age != null ? age : AgeType.UNDEFINED;
        this.profileImgUrl = profileImgUrl != null ? profileImgUrl : "";
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
