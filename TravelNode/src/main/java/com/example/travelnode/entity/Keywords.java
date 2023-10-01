package com.example.travelnode.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "KEYWORDS")
public class Keywords {

    @Id
    @Column(name = "KEY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keyId;

    @NotNull
    @Column(name = "KEYWORD")
    private String keyword;
}