package com.example.travelnode.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CITY")
//@Setter
public class City {

    @Id
    @Column(name = "CITY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotNull
    @Column(name = "CITY_NAME")
    private String cityName;


    @Builder
    public City(String cityName){

        this.cityName = cityName;
    }


}
