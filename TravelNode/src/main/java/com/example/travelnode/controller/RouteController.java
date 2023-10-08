package com.example.travelnode.controller;

import com.example.travelnode.dto.*;
import com.example.travelnode.entity.*;
import com.example.travelnode.oauth2.entity.UserPrincipal;
import com.example.travelnode.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
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
    public RouteResponseDto registerRoute(@RequestBody RouteCreateRequestDto requestDto) {

        DefaultOAuth2User user = (DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal userPrincipal = new UserPrincipal(user.getName(), user.getAttributes().get("email").toString(),
                user.getAttributes().get("nickname").toString(), RoleType.USER, ProviderType.KAKAO,
                (Collection<GrantedAuthority>) user.getAuthorities());

        if(Objects.equals(userPrincipal.getRoleType().getCode(), RoleType.GUEST.getCode())) {
            throw new AccessDeniedException("Do not have authority");
        }

        return routeService.createRoute(requestDto, userPrincipal);
    }

    @PatchMapping("/update-city/{routeId}") // 도시 수정
    public String updateCity(@PathVariable Long routeId, @RequestParam Long cityId) {

        return routeService.updateCity(routeId, cityId);
    }

    @PatchMapping("/update-keyword/{routeId}") // 키워드 수정
    public Keywords updateKeyword(@PathVariable Long routeId, @RequestParam Long currentKey, @RequestParam Long newKey) {

        return routeService.updateKeyword(routeId, currentKey, newKey);
    }

    @PatchMapping("/update-name/{routeId}") // 루트 이름 수정
    public String updateRouteName(@PathVariable Long routeId, @RequestParam String routeName) {

        return routeService.updateRouteName(routeId, routeName);
    }

    @PatchMapping("/update-date/{routeId}") // 날짜 수정
    public LocalDate updateDay(@PathVariable Long routeId, @RequestParam @DateTimeFormat(
            pattern = "yyyy-MM-dd") LocalDate routeDay) {

        return routeService.updateRouteDay(routeId, routeDay);
    }

    @PatchMapping("/update-private/{routeId}") // 루트 공개 여부 결정
    public Boolean updatePrivate(@PathVariable Long routeId, @RequestParam Boolean isPrivate) {

        return routeService.updateRoutePrivate(routeId, isPrivate);
    }

    @DeleteMapping("/delete/{routeId}") // 루트 삭제
    public void deleteRoute(@PathVariable Long routeId){

        routeService.deleteRoute(routeId);
    }
}