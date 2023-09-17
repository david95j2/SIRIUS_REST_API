package com.example.sirius_restapi.mission.local.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "type")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private String type;
    @Column(name = "line_auto")
    private Boolean lineAuto;
    @Column(name = "line_direction")
    private String lineDirection;
    @Column(name = "circle_inward")
    private Boolean circleInward;
    @Column(name = "circle_start_angle")
    private Float circleStartAngle;
    @Column(name = "rect_inward")
    private Boolean rectInward;
    @Column(name = "bottom_auto")
    private Boolean bottomAuto;
    @Column(name = "bottom_whole")
    private Boolean bottomWhole;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "local_mission_id")
    private LocalMissionEntity localMissionEntity;
}
