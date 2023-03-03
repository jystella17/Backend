package com.example.travelnode.service;

import com.example.travelnode.dto.CityCreateRequestDto;
import com.example.travelnode.entity.City;
import com.example.travelnode.entity.Route;
import com.example.travelnode.repository.CityRepository;
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

    // city Dto 받아서 city저장 후 -> route에 저장하기
    @Transactional
    public Long createcity(CityCreateRequestDto requestDto) {
        Optional<City> city = cityRepository.findById(requestDto.getCityId());
        Route route = Route.builder()
                .city(city.orElseThrow())
                .build();
        return routeRepository.save(route).getRouteId();
    }

    // city Id 받아서 -> city DB에 저장된 city 가져옴 -> route에 저장하기

}
