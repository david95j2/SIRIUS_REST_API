package com.example.sirius_restapi.mission.local.domain;

import com.example.sirius_restapi.map.domain.MapGroupEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "local_waypoints")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocalWaypointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private String seq;
    @Column(name = "pos_x")
    private Float posX;
    @Column(name = "pos_y")
    private Float posY;
    @Column(name = "pos_z")
    private Float posZ;
    private Float yaw;
    private Boolean checked;
    private Boolean completed;
    @Column(name = "group_num")
    private Integer groupNum;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "local_mission_id")
    private LocalMissionEntity localMissionEntity;
}
