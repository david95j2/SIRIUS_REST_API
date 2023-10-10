package com.example.sirius_restapi.mission.plan.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "local_drones")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocalDroneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_mission_id")
    private PlansEntity plansEntity;

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "localDroneEntity",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LocalWaypointEntity> localWaypointEntities;

    public PatchLocalDroneRes toDto() {
        PatchLocalDroneRes patchLocalDroneRes = new PatchLocalDroneRes();
        patchLocalDroneRes.setId(this.id);
        patchLocalDroneRes.setName(this.getName());
        return patchLocalDroneRes;
    }

    // PostFitiingsReq의 필드명 및 타입이 일치하기 때문에, 별도로(PostLocalMissionReq) 생성하지않고 빌려씀.
    public static LocalDroneEntity from(PostLocalMissionReq postLocalMissionReq, PlansEntity plansEntity) {
        return LocalDroneEntity.builder()
                .plansEntity(plansEntity)
                .name(postLocalMissionReq.getName())
                .build();
    }
}
