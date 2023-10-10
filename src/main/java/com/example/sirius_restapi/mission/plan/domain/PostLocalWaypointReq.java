package com.example.sirius_restapi.mission.plan.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostLocalWaypointReq {
    @NotNull(message = "seq은 필수 입력값입니다. 키가 seq인지 확인해주세요.")
    private Integer seq;
    @NotNull(message = "pos_x은 필수 입력값입니다. 키가 pos_x인지 확인해주세요.")
    private Float pos_x;
    @NotNull(message = "pos_y은 필수 입력값입니다. 키가 pos_y인지 확인해주세요.")
    private Float pos_y;
    @NotNull(message = "pos_z은 필수 입력값입니다. 키가 pos_z인지 확인해주세요.")
    private Float pos_z;
    @NotNull(message = "yaw은 필수 입력값입니다. 키가 yaw인지 확인해주세요.")
    private Float yaw;
    @NotNull(message = "checked은 필수 입력값입니다. 키가 checked인지 확인해주세요.")
    private Boolean checked;
    @NotNull(message = "completed은 필수 입력값입니다. 키가 completed인지 확인해주세요.")
    private Boolean completed;
    @NotNull(message = "group_num은 필수 입력값입니다. 키가 group_num인지 확인해주세요.")
    private Integer group_num;
}
