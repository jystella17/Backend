package com.example.travelnode.dto;

import com.example.travelnode.entity.Avatar;
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
    private String nickname;
    private RoleType roleType;
    private ProviderType providerType;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Avatar avatar;
    private Integer travelCount;
    private Integer level;
}
