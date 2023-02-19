package com.example.travelnode.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "KEYWORD")
public class Keyword {

    @Id
    @Column(name = "KEY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keyId;

    
}
