package com.example.travelnode.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "REVIEW")
public class Review {

    @Id
    @Column(name = "REVIEW_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private User user;

    @NotNull
    @OneToOne
    @JoinColumn(name = "PLACE_ID", foreignKey = @ForeignKey(name = "fk_review_place"))
    private RoutePlace routePlace;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID", foreignKey = @ForeignKey(name = "fk_review_comment"))
    private Comment comment;

    @Column(name = "REVIEW_TEXT")
    private String reviewText;

    @Column(name = "REVIEW_IMGS")
    private String reviewImgs;
}
