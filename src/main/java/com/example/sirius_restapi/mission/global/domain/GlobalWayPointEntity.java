package com.example.sirius_restapi.mission.global.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "global_waypoints")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GlobalWayPointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private Integer seq;
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private String wait;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "global_mission_id")
    private GlobalMissionEntity globalMissionEntity;

    public static GlobalWayPointEntity from(PostGlobalWaypointReq postGlobalWayPointReq, GlobalMissionEntity globalMissionEntity) {
        return GlobalWayPointEntity.builder()
                .seq(postGlobalWayPointReq.getSeq())
                .latitude(postGlobalWayPointReq.getLatitude())
                .longitude(postGlobalWayPointReq.getLongitude())
                .altitude(postGlobalWayPointReq.getAltitude())
                .wait(postGlobalWayPointReq.getWait())
                .globalMissionEntity(globalMissionEntity)
                .build();
    }
}
