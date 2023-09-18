package com.example.sirius_restapi.mission.global.domain;

import com.example.sirius_restapi.user.domain.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "global_missions")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GlobalMissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "globalMissionEntity",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GlobalWayPointEntity> globalWayPointEntities;

    public PatchGlobalMissionRes toDto() {
        PatchGlobalMissionRes patchGlobalMissionRes = new PatchGlobalMissionRes();
        patchGlobalMissionRes.setId(this.id);
        patchGlobalMissionRes.setMission_name(this.name);
        return patchGlobalMissionRes;
    }

    public static GlobalMissionEntity from(PostGlobalMissionReq postGlobalMissionReq, UserEntity userEntity) {
        return GlobalMissionEntity.builder()
                .userEntity(userEntity)
                .name(postGlobalMissionReq.getName())
                .build();
    }
}
