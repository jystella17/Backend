package com.example.travelnode.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class PlaceRegisterRequestDto {

    private final String placeName;
    private final Double longitude;
    private final Double latitude;
    private final Integer priority;
    private final LocalDateTime visitTime;

    @Builder
    public PlaceRegisterRequestDto(String placeName, Double longitude, Double latitude,
                                   Integer priority, LocalDateTime visitTime) {
        this.placeName = placeName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.priority = priority;
        this.visitTime = visitTime;
    }
}