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
    private String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fitting_group_id")
    private FittingGroupEntity fittingGroupEntity;

    public PatchLocalMissionRes toDto() {
        PatchLocalMissionRes patchLocalMissionRes = new PatchLocalMissionRes();
        patchLocalMissionRes.setId(this.id);
        patchLocalMissionRes.setName(this.getName());
        return patchLocalMissionRes;
    }

    // PostFitiingsReq의 필드명 및 타입이 일치하기 때문에, 별도로(PostLocalMissionReq) 생성하지않고 빌려씀.
    public static LocalMissionEntity from(PostFittingsReq postFittingsReq,FittingGroupEntity fittingGroupEntity) {
        return LocalMissionEntity.builder()
                .fittingGroupEntity(fittingGroupEntity)
                .name(postFittingsReq.getName())
                .build();
    }
}
