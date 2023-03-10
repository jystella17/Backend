package com.example.travelnode.controller;

import com.example.travelnode.dto.RouteCreateRequestDto;
import com.example.travelnode.dto.CityUpdateRequestDto;
import com.example.travelnode.dto.RouteDayUpdateRequestDto;
import com.example.travelnode.dto.RouteNameUpdateRequestDto;
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

    @PostMapping("/createroute")  // 루트등록
    public Long createcity(@RequestBody RouteCreateRequestDto requestDto){
        return routeService.createcity(requestDto);
    }


    // 루트 도시, 키워드 수정 --> 방법 더 찾아보기..
    @PatchMapping("/update/routecity/{id}")   // 루트이름 수정
    public Long updatecity(@PathVariable Long id, @RequestBody CityUpdateRequestDto requestDto) {
        return routeService.updatecity(id, requestDto);
    }

    @PatchMapping("/update/routename/{id}")   // 루트이름 수정
    public Long updatename(@PathVariable Long id, @RequestBody RouteNameUpdateRequestDto requestDto) {
        return routeService.updateroutename(id, requestDto);
    }

    @PatchMapping("/update/routedate/{id}")   // 루트날짜 수정
    public Long updateday(@PathVariable Long id, @RequestBody RouteDayUpdateRequestDto requestDto) {
        return routeService.updaterouteday(id, requestDto);
    }

    @DeleteMapping("delete/route/{id}") // 루트 전체 삭제
    public void deleteroute(@PathVariable Long id){
        routeService.deleteroute(id);
    }






}