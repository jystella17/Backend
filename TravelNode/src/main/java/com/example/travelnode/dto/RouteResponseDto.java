package com.example.travelnode.dto;

import com.example.travelnode.entity.Route;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RouteResponseDto {

    private Long id;
    private Long cityId;
    private Long keyword1;
    private Long keyword2;
    private String routeName;
    private LocalDate routeDay;
    private boolean open;

    public RouteResponseDto(Route entity){
        this.id = entity.getRouteId();
        this.cityId = entity.getCity().getId();
        this.keyword1 = entity.getKeyword1().getKeyId();
        this.keyword2 = entity.getKeyword2().getKeyId();
        this.routeName = entity.getRouteName();
        this.routeDay = entity.getRouteDay();
        this.open = entity.getIsOpened();
    }


}
