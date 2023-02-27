package com.example.travelnode.service;

import com.example.travelnode.dto.CityCreateRequestDto;
import com.example.travelnode.entity.City;
import com.example.travelnode.entity.Route;
import com.example.travelnode.entity.User;
import com.example.travelnode.repository.CityRepository;
import com.example.travelnode.repository.RouteRepository;
import com.example.travelnode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RouteService {

    private final RouteRepository routeRepository;

    private final CityRepository cityRepository;

    //private final UserRepository userRepository;

    @Transactional  // 지역 선택
    public Long createcity(CityCreateRequestDto requestDto){
        Optional<City> city = cityRepository.findById(requestDto.getCityId());
        // Optional<User> user = userRepository.findById(requestDto.getUid());
        Route route = Route.builder()
                .city(city.orElseThrow())
                .build();
        return routeRepository.save(route).getRouteId();

    }
}
