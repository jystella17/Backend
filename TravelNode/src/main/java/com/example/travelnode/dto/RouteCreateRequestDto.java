package com.example.travelnode.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteCreateRequestDto {

    private Long cityId;
    private Long keyId1;
    private Long keyId2;
    private String routeName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate routeDay;




}
