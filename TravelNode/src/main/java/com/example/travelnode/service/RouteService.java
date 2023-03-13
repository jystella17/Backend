package com.example.travelnode.service;

import com.example.travelnode.dto.*;
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

    // 도시 , 키워드 수정 service
    public Long updatecity(Long id, com.example.travelnode.dto.CityUpdateRequestDto requestDto) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("해당 Route 가  존재하지 않습니다.")));
        Optional<City> city = cityRepository.findById(requestDto.getCityId());
        route.updatecity(city.orElseThrow());

        routeRepository.save(route);
        return id;
    }

    public Long updatekeyword(Long id, com.example.travelnode.dto.KeywordUpdateRequestDto requestDto) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 Route 가 존재하지 않습니다."));
        Optional<KeywordList> keyword1 = keywordRepository.findById(requestDto.getKeyword1());
        Optional<KeywordList> keyword2 = keywordRepository.findById(requestDto.getKeyword2());
        route.updatekeyword(keyword1.orElseThrow(), keyword2.orElseThrow());

        routeRepository.save(route);
        return id;

    }

    @Transactional
    public Long updateroutename(Long id, com.example.travelnode.dto.RouteNameUpdateRequestDto requestDto) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("해당 Route 가  존재하지 않습니다.")));
        route.updateroutename(requestDto);
        // Dirty Cheking 왜 안되는거지..
        routeRepository.save(route);
        return id;
    }


    public Long updaterouteday(Long id, com.example.travelnode.dto.RouteDayUpdateRequestDto requestDto) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("해당 Route 가  존재하지 않습니다.")));
        route.updaterouteday(requestDto);
        // Dirty Cheking
        routeRepository.save(route);
        return id;
    }

    public void deleteroute(Long id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("해당 Route 가  존재하지 않습니다.")));
        routeRepository.delete(route);
    }


    // 루트별 조회
    public RouteResponseDto searchByRouteId(Long id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("해당 Route 가  존재하지 않습니다.")));
        return new RouteResponseDto(route);
    }

    // 루트 공개 여부
    public Long updaterouteopen(Long id, RouteOpenUpdateRequestDto requestDto) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("해당 Route 가  존재하지 않습니다.")));
        route.updaterouteopen(requestDto.isOpen());
        routeRepository.save(route);
        return id;
    }
}


