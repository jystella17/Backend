package com.example.travelnode.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
public class RouteCreateRequestDto implements Serializable {

    private final Long cityId;
    private final List<Long> keywords;
    private final String routeName;
    private final Boolean isPrivate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate routeDay;
    private final List<PlaceRegisterRequestDto> places;
    private final List<ReviewRequestDto> reviews;

    @Builder
    public RouteCreateRequestDto(Long cityId, List<Long> keywords, String routeName, Boolean isPrivate,
                                 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate routeDay,
                                 List<PlaceRegisterRequestDto> places, List<ReviewRequestDto> reviews) {
        this.cityId = cityId;
        this.keywords = keywords;
        this.routeName = routeName;
        this.isPrivate = isPrivate;
        this.routeDay = routeDay;
        this.places = places;
        this.reviews = reviews;
    }
}
