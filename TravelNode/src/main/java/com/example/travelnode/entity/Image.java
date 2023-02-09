package com.example.travelnode.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "IMAGE")
public class Image {

    @Id
    @Column(name = "IMG_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgId;

    @OneToOne
    @JoinColumn(name = "avatarId")
    private Avatar avatar;

    @OneToOne
    @JoinColumn(name = "preferId")
    private PreferenceList preferenceList;

    @JsonIgnore
    @Column(name = "IMG_NAME")
    private String fileName;

    @JsonIgnore
    @Column(name = "IMG_KEY")
    private String fileKey;

    @NotNull
    @Column(name = "IMG_PATH")
    private String filePath;
}