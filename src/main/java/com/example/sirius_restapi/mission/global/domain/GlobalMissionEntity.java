package com.example.sirius_restapi.mission.global.domain;

import com.example.sirius_restapi.user.domain.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "missions")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GlobalMissionEntity {
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

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "globalMissionEntity",cascade = CascadeType.REMOVE)
    private List<GlobalWayPointEntity> globalWayPointEntities;

    public PatchGlobalMissionRes toDto() {
        PatchGlobalMissionRes patchGlobalMissionRes = new PatchGlobalMissionRes();
        patchGlobalMissionRes.setId(this.id);
        patchGlobalMissionRes.setMission_name(this.missionName);
        patchGlobalMissionRes.setType(this.missionType);
        return patchGlobalMissionRes;
    }

    public static GlobalMissionEntity from(PostGlobalMissionReq postGlobalMissionReq, UserEntity userEntity) {
        return GlobalMissionEntity.builder()
                .userEntity(userEntity)
                .missionName(postGlobalMissionReq.getMission_name())
                .missionType(postGlobalMissionReq.getType())
                .build();
    }
}
