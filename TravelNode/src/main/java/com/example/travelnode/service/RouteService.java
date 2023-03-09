package com.example.travelnode.service;

import com.example.travelnode.dto.RouteCreateRequestDto;
import com.example.travelnode.dto.CityUpdateRequestDto;
import com.example.travelnode.entity.City;
import com.example.travelnode.entity.KeywordList;
import com.example.travelnode.entity.Route;
import com.example.travelnode.repository.CityRepository;
import com.example.travelnode.repository.KeywordRepository;
import com.example.travelnode.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final CityRepository cityRepository;
    private final RouteRepository routeRepository;

    private final KeywordRepository keywordRepository;


    @Transactional // 도시 루트에 저장
    public Long createcity(RouteCreateRequestDto requestDto) {
        Optional<City> city = cityRepository.findById(requestDto.getCityId());
        Optional<KeywordList> keyword1 = keywordRepository.findById(requestDto.getKeyId1());
        Optional<KeywordList> keyword2 = keywordRepository.findById(requestDto.getKeyId2());
        Route route = Route.builder()
                .city(city.orElseThrow())
                .keyword1(keyword1.orElseThrow())
                .keyword2(keyword2.orElseThrow())
                .routeName(requestDto.getRouteName())
                .routeDay(requestDto.getRouteDay())
                .build();
        return routeRepository.save(route).getRouteId();
    }
    /*
    public Long updatecity(Long id, CityUpdateRequestDto requestDto) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("해당 Route 가  존재하지 않습니다.")));
        route.updatecity(requestDto);
        // Dirty Cheking
        return id;
    }

     */





}
