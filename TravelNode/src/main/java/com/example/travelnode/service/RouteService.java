package com.example.travelnode.service;

import com.example.travelnode.dto.CityCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class RouteService {


    @Transactional  // 지역 선택
    public Long createcity(CityCreateRequestDto requestDto){

    }
}
