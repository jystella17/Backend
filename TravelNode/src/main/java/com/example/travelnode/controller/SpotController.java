package com.example.travelnode.controller;

import com.example.travelnode.dto.SpotInfoDto;
import com.example.travelnode.entity.SpotInfo;
import com.example.travelnode.service.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/spot")
@RequiredArgsConstructor
public class SpotController {

    private final SpotService spotService;

    @GetMapping("/list")
    public List<SpotInfo> getSpotLists() {

        return spotService.getAllSpots();
    }

    @PostMapping("/register")
    public SpotInfo registerSpot(SpotInfoDto spotInfoDto) {

        return spotService.registerSpotInfo(spotInfoDto);
    }

    @PutMapping("/change-name")
    public SpotInfo updateSpotName(String prevName, String spotName) {

        return spotService.updateSpotName(prevName, spotName);
    }

    @DeleteMapping("/delete/{spotName}")
    public void deleteSpot(@PathVariable(value = "spotName") String spotName) {

        spotService.deleteSpotInfo(spotName);
    }
}
