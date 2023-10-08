package com.example.travelnode.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Table(name = "ROUTE")
public class Route {

    @Id
    @Column(name = "ROUTE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;

    //@NotNull
    @ManyToOne
    @JoinColumn(name = "UID", foreignKey = @ForeignKey(name = "fk_route_uid"))
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CITY_ID", foreignKey = @ForeignKey(name = "fk_route_city"))
    private City city;

    @OneToMany(mappedBy = "keyId")
    private List<Keywords> keywordsList = new ArrayList<>();

    @NotNull
    @Size(max = 128)
    @Column(name = "ROUTE_NAME", length = 128)
    private String routeName;

    @NotNull
    @Column(name = "SCRAP_COUNT")
    private Integer scrapCount;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Column(name = "ROUTE_DAY")
    private LocalDate routeDay;

    @ColumnDefault("false")
    @Column(name = "IS_PRIVATE")
    private Boolean isPrivate;

    @ColumnDefault("false")
    @Column(name = "IS_FOLLOWING")
    private Boolean isFollowing;

    public void addScrap() {
        this.scrapCount++;
    }

    public void subScrap() {
        this.scrapCount--;
    }

    @Builder
    public Route(User user, City city, List<Keywords> keywordsList, String routeName,
                 Boolean isPrivate, LocalDate routeDay, Integer scrapCount) {
        Assert.hasText(String.valueOf(user), "User must not be empty");
        Assert.hasText(String.valueOf(city), "City  must not be empty");
        Assert.hasText(routeName, "Route Name must not be empty");
        Assert.hasText(String.valueOf(routeDay), "Route Day must not be empty");

        this.user = user;
        this.city = city;
        this.keywordsList = keywordsList;
        this.routeName = routeName;
        this.routeDay = routeDay;
        this.isPrivate = isPrivate;
        this.scrapCount = scrapCount != null ? scrapCount : 0;
    }

    public void updateCity(City city){
        this.city = city;
    }

    public void updateRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void updateRouteDay(LocalDate routeDay) {
        this.routeDay = routeDay;
    }

    public void updateKeyword(int idx, Keywords keyword) {
        this.keywordsList.set(idx, keyword);
    }

    public void updateRoutePrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }
}