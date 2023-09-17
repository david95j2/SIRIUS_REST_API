package com.example.sirius_restapi.mission.local.domain;

import com.example.sirius_restapi.map.domain.MapGroupEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "local_waypoints")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocalWaypointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private Integer seq;
    @Column(name = "pos_x")
    private Float posX;
    @Column(name = "pos_y")
    private Float posY;
    @Column(name = "pos_z")
    private Float posZ;
    private Float yaw;
    private Boolean checked;
    private Boolean completed;
    @Column(name = "group_num")
    private Integer groupNum;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_mission_id")
    private LocalMissionEntity localMissionEntity;

    public PatchLocalWaypointRes toDto() {
        PatchLocalWaypointRes patchLocalWaypointRes = new PatchLocalWaypointRes();
        patchLocalWaypointRes.setId(this.id);
        patchLocalWaypointRes.setSeq(this.seq);
        patchLocalWaypointRes.setPos_x(this.posX);
        patchLocalWaypointRes.setPos_y(this.posY);
        patchLocalWaypointRes.setPos_z(this.posZ);
        patchLocalWaypointRes.setYaw(this.yaw);
        patchLocalWaypointRes.setChecked(this.checked);
        patchLocalWaypointRes.setCompleted(this.completed);
        patchLocalWaypointRes.setGroup_num(this.getGroupNum());
        return patchLocalWaypointRes;
    }

    public static LocalWaypointEntity from(PostLocalWaypointReq postLocalWaypointReq, LocalMissionEntity localMissionEntity) {
        return LocalWaypointEntity.builder()
                .localMissionEntity(localMissionEntity)
                .seq(postLocalWaypointReq.getSeq())
                .posX(postLocalWaypointReq.getPos_x())
                .posY(postLocalWaypointReq.getPos_y())
                .posZ(postLocalWaypointReq.getPos_z())
                .yaw(postLocalWaypointReq.getYaw())
                .checked(postLocalWaypointReq.getChecked())
                .completed(postLocalWaypointReq.getCompleted())
                .groupNum(postLocalWaypointReq.getGroup_num())
                .build();
    }
}
