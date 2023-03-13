package com.example.travelnode.controller;

import com.example.travelnode.dto.*;
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

    @GetMapping("/route/{id}") // 루트별 루트 조회
    public RouteResponseDto searchById(@PathVariable Long id) {
        return routeService.searchByRouteId(id);
    }

    @PostMapping("/createroute")  // 루트등록
    public Long createcity(@RequestBody RouteCreateRequestDto requestDto){
        return routeService.createcity(requestDto);
    }

    @PatchMapping("/update/routecity/{id}")   // 루트도시 수정
    public Long updatecity(@PathVariable Long id, @RequestBody CityUpdateRequestDto requestDto) {
        return routeService.updatecity(id, requestDto);
    }

    @PatchMapping("/update/routekeyword/{id}")   // 루트키워드 수정
    public Long updatekeyword(@PathVariable Long id, @RequestBody KeywordUpdateRequestDto requestDto) {
        return routeService.updatekeyword(id, requestDto);
    }

    @PatchMapping("/update/routename/{id}")   // 루트이름 수정
    public Long updatename(@PathVariable Long id, @RequestBody RouteNameUpdateRequestDto requestDto) {
        return routeService.updateroutename(id, requestDto);
    }

    @PatchMapping("/update/routedate/{id}")   // 루트날짜 수정
    public Long updateday(@PathVariable Long id, @RequestBody RouteDayUpdateRequestDto requestDto) {
        return routeService.updaterouteday(id, requestDto);
    }

    @PatchMapping("/update/routeopen/{id}")   // 루트 공개여부 결정
    public Long updateopen(@PathVariable Long id, @RequestBody RouteOpenUpdateRequestDto requestDto) {
        return routeService.updaterouteopen(id, requestDto);
    }

    @DeleteMapping("delete/route/{id}") // 루트 전체 삭제
    public void deleteroute(@PathVariable Long id){
        routeService.deleteroute(id);
    }


}