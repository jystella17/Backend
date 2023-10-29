package com.example.travelnode.dto;

import com.example.travelnode.entity.Keywords;
import com.example.travelnode.entity.Route;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class RouteResponseDto {

    private final Long id;
    private final Long cityId;
    private final List<Keywords> keywordsList;
    private final String routeName;
    private final LocalDate routeDay;
    private final boolean open;

    public RouteResponseDto(Route entity){
        this.id = entity.getRouteId();
        this.cityId = entity.getCity().getId();
        this.keywordsList = entity.getKeywordsList();
        this.routeName = entity.getRouteName();
        this.routeDay = entity.getRouteDay();
        this.open = entity.getIsPrivate();
    }

    @Builder
    public RouteResponseDto(Long id, Long cityId, List<Keywords> keywordsList,
                            String routeName, LocalDate routeDay, boolean open) {
        this.id = id;
        this.cityId = cityId;
        this.keywordsList = keywordsList;
        this.routeName = routeName;
        this.routeDay = routeDay;
        this.open = open;
    }
}