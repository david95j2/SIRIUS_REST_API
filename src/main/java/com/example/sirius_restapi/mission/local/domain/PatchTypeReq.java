package com.example.sirius_restapi.mission.local.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PatchTypeReq {
    @Pattern(regexp = "^(line|rectangle|circle|underside|waypoint)$", message = "type은 다음과 같습니다. [line/rectangle/circle/underside/waypoint]")
    private String type;
    private Boolean line_auto;
    private String line_direction;
    private Boolean circle_inward;
    private Float circle_start_angle;
    private Boolean rect_inward;
    private Boolean bottom_auto;
    private Boolean bottom_whole;
}
