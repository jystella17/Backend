package com.example.travelnode.dto;

import com.example.travelnode.entity.Route;
import com.example.travelnode.entity.RoutePlace;
import com.example.travelnode.entity.SpotInfo;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class RoutePlaceDto implements Serializable {
    private final SpotInfo spot;
    private final String placeName;
    private final String routeName;
    // private final Route route;
    private final Integer priority;
    private final LocalDateTime visitTime;

    public RoutePlace toEntity() {

        return RoutePlace.builder().spot(spot).placeName(placeName).routeName(routeName)
                .priority(priority).visitTime(visitTime).build();
    }

    @Builder
    public RoutePlaceDto(SpotInfo spot, String placeName, String routeName, Integer priority, LocalDateTime visitTime) {
        this.spot = spot;
        this.placeName = placeName;
        this.routeName = routeName;
        this.priority = priority;
        this.visitTime = visitTime;
    }
}
