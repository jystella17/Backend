package com.example.travelnode.dto;

//import com.example.travelnode.entity.Avatar;
import com.example.travelnode.entity.ProviderType;
import com.example.travelnode.entity.RoleType;
import com.example.travelnode.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoDto implements Serializable {
    private Long uid;
    private final String email;
    private final String nickname;
    private final RoleType roleType;
    private final ProviderType providerType;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    // private Avatar avatar;
    private final Integer travelCount;
    private final Integer level;

    @Builder
    public UserInfoDto(User user) { // 인증(로그인) 된 사용자 정보
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        // this.avatar = user.getAvatar();
        this.roleType = user.getRoleType();
        this.providerType = user.getProviderType();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
        this.travelCount = user.getTravelCount();
        this.level = user.getLevel();
    }
}
