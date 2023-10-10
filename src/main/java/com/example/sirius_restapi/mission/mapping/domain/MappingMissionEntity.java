package com.example.sirius_restapi.mission.mapping.domain;

import com.example.sirius_restapi.user.domain.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "mapping_missions")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MappingMissionEntity {
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
    @OneToMany(mappedBy = "mappingMissionEntity",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MappingWayPointEntity> globalWayPointEntities;

    public PatchMappingMissionRes toDto() {
        PatchMappingMissionRes patchMappingMissionRes = new PatchMappingMissionRes();
        patchMappingMissionRes.setId(this.id);
        patchMappingMissionRes.setMission_name(this.name);
        return patchMappingMissionRes;
    }

    public static MappingMissionEntity from(PostMappingMissionReq postMappingMissionReq, UserEntity userEntity) {
        return MappingMissionEntity.builder()
                .userEntity(userEntity)
                .name(postMappingMissionReq.getName())
                .build();
    }
}
