package com.example.sirius_restapi.mission.local.domain;

import com.example.sirius_restapi.map.domain.MapGroupEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "local_missions")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocalMissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "mission_name")
    private String missionName;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fitting_group_id")
    private FittingGroupEntity fittingGroupEntity;
}
