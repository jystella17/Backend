package com.example.travelnode.dto;

import com.example.travelnode.entity.Route;
import com.example.travelnode.entity.RoutePlace;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class PlaceResponseDto {

    private final String placeName;
    private final String address;
    private final Long routeId;
    private final String routeName;
    private final int priority;
    private final LocalDateTime visitTime;

    public PlaceResponseDto(RoutePlace routePlace) {
        this.placeName = routePlace.getPlaceName();
        this.address = routePlace.getSpot().getAddress();
        this.routeId = routePlace.getRoute().getRouteId();
        this.routeName = routePlace.getRoute().getRouteName();
        this.priority = routePlace.getPriority();
        this.visitTime = routePlace.getVisitTime();
    }

    @Builder
    public PlaceResponseDto(String placeName, String address, Long routeId, String routeName,
                            int priority, LocalDateTime visitTime) {
        this.placeName = placeName;
        this.address = address;
        this.routeId = routeId;
        this.routeName = routeName;
        this.priority = priority;
        this.visitTime = visitTime;
    }
}