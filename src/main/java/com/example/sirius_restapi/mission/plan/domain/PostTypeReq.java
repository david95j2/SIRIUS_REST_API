package com.example.sirius_restapi.mission.plan.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostTypeReq {
    @NotBlank(message = "type은 필수 입력값입니다. 키가 type인지 확인해주세요.")
    @Pattern(regexp = "^(line|rectangle|circle|underside|waypoint)$", message = "type은 다음과 같습니다. [line/rectangle/circle/underside/waypoint]")
    private String type;
    @NotNull(message = "line_auto은 필수 입력값입니다. 키가 line_auto인지 확인해주세요.")
    private Boolean line_auto;
    @NotBlank(message = "line_direction은 필수 입력값입니다. 키가 line_direction인지 확인해주세요.")
    private String line_direction;
    @NotNull(message = "circle_inward은 필수 입력값입니다. 키가 circle_inward인지 확인해주세요.")
    private Boolean circle_inward;
    @NotNull(message = "circle_start_angle은 필수 입력값입니다. 키가 circle_start_angle인지 확인해주세요.")
    private Float circle_start_angle;
    @NotNull(message = "rect_inward은 필수 입력값입니다. 키가 rect_inward인지 확인해주세요.")
    private Boolean rect_inward;
    @NotNull(message = "bottom_auto은 필수 입력값입니다. 키가 bottom_auto인지 확인해주세요.")
    private Boolean bottom_auto;
    @NotNull(message = "bottom_whole은 필수 입력값입니다. 키가 bottom_whole인지 확인해주세요.")
    private Boolean bottom_whole;
}
