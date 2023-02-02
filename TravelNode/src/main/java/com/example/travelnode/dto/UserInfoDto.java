package com.example.travelnode.dto;

import com.example.travelnode.entity.AgeType;
import com.example.travelnode.entity.GenderType;
import com.example.travelnode.entity.ProviderType;
import com.example.travelnode.entity.RoleType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoDto implements Serializable {
    private Long uid;
    private String email;
    private String userId;
    private String nickname;
    private RoleType roleType;
    private ProviderType providerType;
    private GenderType gender;
    private AgeType age;
    private String profileImgUrl;
    private String profileImgName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
