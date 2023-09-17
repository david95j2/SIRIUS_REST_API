package com.example.sirius_restapi.mission.local.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PatchLocalWaypointReq {
    private Integer seq;
    private Float pos_x;
    private Float pos_y;
    private Float pos_z;
    private Float yaw;
    private Boolean checked;
    private Boolean completed;
    private Integer group_num;
}
