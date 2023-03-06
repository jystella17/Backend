package com.example.travelnode.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class PlaceRegisterRequestDto implements Serializable {
    private final String spotName;
    private final String address;
    private final String routeName;
    private final Double longitude;
    private final Double latitude;
    private final Integer priority;

    @Builder
    public PlaceRegisterRequestDto(String spotName, String address, String routeName,
                                   Double longitude, Double latitude, Integer priority) {
        this.spotName = spotName;
        this.address = address;
        this.routeName = routeName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.priority = priority;
    }
}
