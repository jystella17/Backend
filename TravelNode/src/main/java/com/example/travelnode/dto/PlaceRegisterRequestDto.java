package com.example.travelnode.dto;

import com.example.travelnode.entity.SpotInfo;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SpotInfoDto implements Serializable {
    private final String spotName;
    private final String address;
    private final Double longitude;
    private final Double latitude;

    public SpotInfoDto(SpotInfo spotInfo) {
        this.spotName = spotInfo.getSpotName();
        this.address = spotInfo.getAddress();
        this.longitude = spotInfo.getLongitude();
        this.latitude = spotInfo.getLatitude();
    }
}
