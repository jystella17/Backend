package com.example.travelnode.controller;

import com.example.travelnode.dto.CityCreateRequestDto;
import com.example.travelnode.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/route")
@RequiredArgsConstructor
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping("/createcity")
    public Long createcity(@RequestBody CityCreateRequestDto requestDto){

        return routeService.createcity(requestDto);
    }

}