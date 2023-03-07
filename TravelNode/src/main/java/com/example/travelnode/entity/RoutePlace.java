package com.example.travelnode.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "ROUTE_PLACE")
public class RoutePlace {

    @Id
    @Column(name = "PLACE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "SPOT_ID", foreignKey = @ForeignKey(name = "fk_place_spot"))
    private SpotInfo spot;

    @NotNull
    @Column(name = "PLACE_NAME")
    private String placeName;

    @NotNull
    @Column(name = "ROUTE_NAME")
    private String routeName;
    /**
    @NotNull
    @ManyToOne
    @JoinColumn(name = "ROUTE_ID", foreignKey = @ForeignKey(name = "fk_place_route"))
    private Route route;
     **/

    @NotNull
    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "VISIT_TIME")
    private LocalDateTime visitTime;

    @Builder
    public RoutePlace(@NotNull SpotInfo spot, @NotNull String placeName, @NotNull String routeName, @NotNull Integer priority, LocalDateTime visitTime) {
        this.spot = spot;
        this.placeName = placeName;
        this.routeName = routeName;
        this.priority = priority;
        this.visitTime = visitTime != null ? visitTime : LocalDateTime.now();
    }

    @Builder
    public RoutePlace() {

    }

    public void update(String placeName) {
        this.placeName = placeName;
    }

    public void changePriority(Integer priority) {
        this.priority = priority;
    }
}
