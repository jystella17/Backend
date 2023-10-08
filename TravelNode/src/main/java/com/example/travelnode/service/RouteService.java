package com.example.travelnode.service;

import com.example.travelnode.dto.*;
import com.example.travelnode.entity.City;
import com.example.travelnode.entity.Keywords;
import com.example.travelnode.entity.Route;
import com.example.travelnode.entity.User;
import com.example.travelnode.oauth2.entity.UserPrincipal;
import com.example.travelnode.repository.CityRepository;
import com.example.travelnode.repository.KeywordRepository;
import com.example.travelnode.repository.RouteRepository;
import com.example.travelnode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final RouteRepository routeRepository;
    private final KeywordRepository keywordRepository;

    @Transactional // 루트별 조회
    public RouteResponseDto searchByRouteId(Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException(("This route does not exist.")));
        return new RouteResponseDto(route);
    }

    @Transactional // 루트 정보 저장
    public RouteResponseDto createRoute(RouteCreateRequestDto requestDto, UserPrincipal userPrincipal) {
        User user = userRepository.findByUniqueId(userPrincipal.getUniqueId());
        City city = cityRepository.findById(requestDto.getCityId()).orElseThrow();

        List<Keywords> keywords = new ArrayList<>();
        for(int i=0; i<2; i++) {
            keywords.add(keywordRepository.findKeywordsByKeyId(requestDto.getKeywords().get(i)));
        }

        Route route = Route.builder().user(user).city(city).keywordsList(keywords)
                .routeName(requestDto.getRouteName()).isPrivate(requestDto.getIsPrivate())
                .routeDay(requestDto.getRouteDay()).scrapCount(0).build();

        route = routeRepository.save(route);
        return new RouteResponseDto(route);
    }

    @Transactional // 여행 도시 수정
    public RouteResponseDto updateCity(Long routeId, Long cityId) {
        Route route = routeRepository.findById(routeId).orElseThrow(
                      () -> new IllegalArgumentException(("Invalid request")));

        City city = cityRepository.findById(cityId).orElseThrow();
        route.updateCity(city);

        return new RouteResponseDto(route);
    }

    @Transactional // 루트 키워드 수정
    public Keywords updateKeyword(Long routeId, Long currentKey, Long newKey) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid request."));

        Keywords keyword = keywordRepository.findById(newKey).orElseThrow();
        if(route.getKeywordsList().get(0).equals(currentKey)) {
            route.updateKeyword(0, keywordRepository.findKeywordsByKeyId(newKey));
        } else {
            route.updateKeyword(1, keywordRepository.findKeywordsByKeyId(newKey));
        }

        return keyword;
    }

    @Transactional
    public String updateRouteName(Long routeId, String routeName) {
        Route route = routeRepository.findById(routeId).orElseThrow(
                () -> new IllegalArgumentException(("Invalid request.")));

        route.updateRouteName(routeName);
        return route.getRouteName();
    }

    @Transactional
    public LocalDate updateRouteDay(Long routeId, LocalDate routeDay) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException(("Invalid request.")));

        route.updateRouteDay(routeDay);
        return route.getRouteDay();
    }

    @Transactional // 루트 공개 여부
    public Boolean updateRoutePrivate(Long routeId, Boolean isPrivate) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException(("Invalid request.")));

        route.updateRoutePrivate(isPrivate);
        return route.getIsPrivate();
    }

    @Transactional
    public void deleteRoute(Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new IllegalArgumentException(("Nothing to delete.")));

        routeRepository.delete(route);
    }
}