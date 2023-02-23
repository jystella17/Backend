package com.example.travelnode.controller;

import com.example.travelnode.dto.CityCreateRequestDto;
import com.example.travelnode.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/route")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;
    @PostMapping("/city")    // 지역 선택
    public Long createcity(@RequestBody CityCreateRequestDto requestDto){
        return routeService.createcity(requestDto);
    }
}
