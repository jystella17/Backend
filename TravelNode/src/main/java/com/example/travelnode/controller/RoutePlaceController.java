package com.example.travelnode.controller;

import com.example.travelnode.dto.PlaceRegisterRequestDto;
import com.example.travelnode.dto.PlaceResponseDto;
import com.example.travelnode.dto.SpotInfoDto;
import com.example.travelnode.entity.RoutePlace;
import com.example.travelnode.service.RoutePlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/place")
@RequiredArgsConstructor
public class RoutePlaceController {

    private final RoutePlaceService routePlaceService;

    // 사용자가 입력한 장소 이름을 기준으로 장소 정보 리턴
    @GetMapping("/details/{loc}")
    public List<SpotInfoDto> placeDetails(@PathVariable String loc, @RequestParam Double longitude,
                                 @RequestParam Double latitude) throws Exception {

        List<SpotInfoDto> spotInfoDto = routePlaceService.locationInfoList(loc, longitude, latitude);
        if(spotInfoDto.isEmpty()) {
            throw new Exception("Cannot Find Information");
        }

        return spotInfoDto;
    }

    // 현재 루트에 포함된 모든 장소 리스트 리턴
    @GetMapping("/place-list")
    public List<PlaceResponseDto> placeListInRoute(@RequestParam Long routeId) {

        return routePlaceService.allPlacesInRoute(routeId);
    }

    // 장소 등록하기 버튼 클릭 시 현재 루트에 해당 장소 저장
    @PostMapping("/register/{routeId}")
    public PlaceResponseDto registerPlace(@PathVariable Long routeId,
                                          @RequestBody PlaceRegisterRequestDto requestDto) throws Exception {

        return routePlaceService.registerRoutePlace(routeId, requestDto);
    }

    @PatchMapping("/{prevName}/change-name")
    public PlaceResponseDto changePlaceName(@PathVariable String prevName, @RequestParam String placeName) {

        return routePlaceService.updatePlaceName(prevName, placeName);
    }

    @DeleteMapping("/delete/{placeId}")
    public void deletePlace(@PathVariable(value = "placeId") Long placeId,
                            @RequestParam String routeName, @RequestParam Integer priority) {
        routePlaceService.deletePlace(placeId, routeName, priority);
    }
}