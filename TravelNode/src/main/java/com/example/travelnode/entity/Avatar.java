package com.example.travelnode.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "AVATAR")
public class Avatar {

    @Id
    @Column(name = "AVATAR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long avatarId;

    @NotNull
    @Size(max = 10)
    @Column(name = "AVATAR_NAME", length = 10)
    private String avatarName;

    @Builder
    public Avatar(@NotNull @Size(max = 10) String avatarName){ // 새로운 아바타 등록
        this.avatarName = avatarName;
    }
}