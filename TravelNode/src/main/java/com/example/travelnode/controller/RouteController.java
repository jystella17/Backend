package com.example.travelnode.controller;

import com.example.travelnode.dto.*;
import com.example.travelnode.entity.*;
import com.example.travelnode.oauth2.entity.UserPrincipal;
// import com.example.travelnode.service.ReviewService;
// import com.example.travelnode.service.RoutePlaceService;
import com.example.travelnode.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1/route")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping("/{routeId}") // 루트 조회
    public RouteResponseDto routeDetails(@PathVariable Long routeId) {

        return routeService.searchByRouteId(routeId);
    }

    // 루트 정보 등록
    @PostMapping(value = "/register")
    public Route registerRoute(@AuthenticationPrincipal UserPrincipal userPrincipal,
                               @RequestPart RouteCreateRequestDto requestDto)
            throws Exception {

        if(userPrincipal == null) {
            throw new UserPrincipalNotFoundException("No User Information");
        }

        if(Objects.equals(userPrincipal.getRoleType().getCode(), RoleType.GUEST.getCode())) {
            throw new AccessDeniedException("Do not have authority");
        }

        return routeService.createRoute(requestDto, userPrincipal);
    }

    @PatchMapping("/update/city/{routeId}") // 루트도시 수정
    public City updateCity(@PathVariable Long routeId, @RequestParam Long cityId) {

        return routeService.updateCity(routeId, cityId);
    }

    /**
    @PatchMapping("/update/keyword/{routeId}") // 루트키워드 수정
    public KeywordList updateKeyword(@PathVariable Long routeId, @RequestParam Long currentKey, @RequestParam Long newKey) {

        return routeService.updateKeyword(routeId, currentKey, newKey);
    }
     **/

    @PatchMapping("/update/name/{routeId}") // 루트이름 수정
    public String updateRouteName(@PathVariable Long routeId, @RequestBody String routeName) {

        return routeService.updateRouteName(routeId, routeName);
    }

    @PatchMapping("/update/date/{routeId}") // 루트날짜 수정
    public LocalDate updateDay(@PathVariable Long routeId, @RequestBody @DateTimeFormat(
            pattern = "yyyy-MM-dd") LocalDate routeDay) {

        return routeService.updateRouteDay(routeId, routeDay);
    }

    @PatchMapping("/update/is-private/{routeId}") // 루트 공개여부 결정
    public Boolean updatePrivate(@PathVariable Long routeId, @RequestBody Boolean isPrivate) {

        return routeService.updateRoutePrivate(routeId, isPrivate);
    }

    @DeleteMapping("/delete/{routeId}") // 루트 삭제
    public void deleteRoute(@PathVariable Long routeId){

        routeService.deleteRoute(routeId);
    }
}