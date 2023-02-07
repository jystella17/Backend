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
@Table(name = "PREFERENCE_LIST")
public class PreferenceList {

    @Id
    @Column(name = "PREFER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preferId;

    @NotNull
    @Size(max = 30)
    @Column(name = "DESCRIPTION", length = 30)
    private String description;

    @NotNull
    @JsonProperty
    @Column(name = "PREFER_IMG_URL")
    private String preferImgUrl;

    @JsonIgnore
    @OneToOne(mappedBy = "preferenceList", fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Image image;


    @Builder
    public PreferenceList(@NotNull @Size(max = 30) String description,
                          @NotNull String preferImgUrl){ // 새로운 여행 타입 등록
        this.description = description;
        this.preferImgUrl = preferImgUrl != null ? preferImgUrl : "";
    }
}
