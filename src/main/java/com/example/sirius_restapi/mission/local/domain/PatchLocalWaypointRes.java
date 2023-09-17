package com.example.sirius_restapi.mission.local.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PatchLocalWaypointRes {
    private Integer id;
    private Integer seq;
    private Float pos_x;
    private Float pos_y;
    private Float pos_z;
    private Float yaw;
    private Boolean checked;
    private Boolean completed;
    private Integer group_num;
}
