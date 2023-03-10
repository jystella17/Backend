package com.example.travelnode.dto;

import com.example.travelnode.entity.SpotInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpotInfoDto {

    private final String spotName;
    private final String address;
    private final Double longitude;
    private final Double latitude;

    public SpotInfo toEntity() {
        return SpotInfo.builder().spotName(spotName).address(address)
                .longitude(longitude).latitude(latitude).build();
    }

    @Builder
    public SpotInfoDto(String spotName, String address, Double longitude, Double latitude) {
        this.spotName = spotName;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
