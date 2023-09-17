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

    public static TypeEntity from(PostTypeReq postTypeReq, LocalMissionEntity localMissionEntity) {
        return TypeEntity.builder()
                .localMissionEntity(localMissionEntity)
                .type(postTypeReq.getType())
                .lineAuto(postTypeReq.getLine_auto())
                .lineDirection(postTypeReq.getLine_direction())
                .circleInward(postTypeReq.getCircle_inward())
                .circleStartAngle(postTypeReq.getCircle_start_angle())
                .rectInward(postTypeReq.getRect_inward())
                .bottomAuto(postTypeReq.getBottom_auto())
                .bottomWhole(postTypeReq.getBottom_whole())
                .build();
    }

    public PatchTypeRes toDto() {
        PatchTypeRes patchTypeRes = new PatchTypeRes();
        patchTypeRes.setId(this.id);
        patchTypeRes.setType(this.type);
        patchTypeRes.setLine_auto(this.lineAuto);
        patchTypeRes.setLine_direction(this.lineDirection);
        patchTypeRes.setCircle_inward(this.circleInward);
        patchTypeRes.setCircle_start_angle(this.circleStartAngle);
        patchTypeRes.setRect_inward(this.rectInward);
        patchTypeRes.setBottom_auto(this.bottomAuto);
        patchTypeRes.setBottom_whole(this.bottomWhole);
        return patchTypeRes;
    }
}
