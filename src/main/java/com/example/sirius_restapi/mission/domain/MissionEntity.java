package com.example.sirius_restapi.mission.domain;

import com.example.sirius_restapi.mission.domain.PatchMissionRes;
import com.example.sirius_restapi.user.domain.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "missions")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "mission_name")
    private String missionName;
    @Column(name = "type")
    private String missionType;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public PatchMissionRes toDto() {
        PatchMissionRes patchMissionRes = new PatchMissionRes();
        patchMissionRes.setId(this.id);
        patchMissionRes.setMission_name(this.missionName);
        patchMissionRes.setType(this.missionType);
        return patchMissionRes;
    }
}
