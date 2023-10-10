package com.example.sirius_restapi.mission.plan.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PatchTypeRes {
    private Integer id;
    private String type;
    private Boolean line_auto;
    private String line_direction;
    private Boolean circle_inward;
    private Float circle_start_angle;
    private Boolean rect_inward;
    private Boolean bottom_auto;
    private Boolean bottom_whole;
}
