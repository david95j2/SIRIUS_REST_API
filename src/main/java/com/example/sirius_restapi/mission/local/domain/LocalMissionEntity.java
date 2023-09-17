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
    @ManyToOne
    @JoinColumn(name = "fitting_group_id")
    private FittingGroupEntity fittingGroupEntity;

    public PatchLocalMissionRes toDto() {
        PatchLocalMissionRes patchLocalMissionRes = new PatchLocalMissionRes();
        patchLocalMissionRes.setId(this.id);
        patchLocalMissionRes.setName(this.getName());
        return patchLocalMissionRes;
    }
}
