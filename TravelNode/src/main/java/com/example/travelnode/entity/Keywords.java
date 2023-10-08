package com.example.travelnode.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "KEYWORDS")
public class Keywords {

    @Id
    @Column(name = "KEY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keyId;

    @NotNull
    @Column(name = "KEYWORD")
    private String keyword;

    @Builder
    public Keywords(Long keyId, String keyword) {
        this.keyId = keyId;
        this.keyword = keyword;
    }
}