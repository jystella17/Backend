package com.example.travelnode.controller;

import com.example.travelnode.dto.RouteCreateRequestDto;
import com.example.travelnode.dto.CityUpdateRequestDto;
import com.example.travelnode.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping("/createroute")  // 도시등록
    public Long createcity(@RequestBody RouteCreateRequestDto requestDto){
        return routeService.createcity(requestDto);
    }

    /*
    @PatchMapping("/updatecity/{id}")   // 도시수정
    public Long updatecity(@PathVariable Long id, @RequestBody CityUpdateRequestDto requestDto) {
        return routeService.updatecity(id, requestDto);
    }

     */




}