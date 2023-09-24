package com.example.travelnode.service;

import com.example.travelnode.dto.SpotInfoDto;
import com.example.travelnode.entity.SpotInfo;
// import com.example.travelnode.repository.SpotInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotService {

    /**
    private final SpotInfoRepository spotInfoRepository;

    public List<SpotInfo> getAllSpots() {

        return spotInfoRepository.findAll();
    }

    @Transactional
    public SpotInfo registerSpotInfo(SpotInfoDto spotInfoDto) {

        return spotInfoRepository.save(spotInfoDto.toEntity());
    }

    @Transactional
    public SpotInfo updateSpotName(String prevName, String spotName) {
        SpotInfo spotInfo = spotInfoRepository.findSpotInfoBySpotName(prevName);
        spotInfo.update(spotName);

        return spotInfo;
    }

    @Transactional
    public void deleteSpotInfo(String spotName) {
        SpotInfo spotInfo = spotInfoRepository.findSpotInfoBySpotName(spotName);
        spotInfoRepository.delete(spotInfo);
    }
    **/
}
