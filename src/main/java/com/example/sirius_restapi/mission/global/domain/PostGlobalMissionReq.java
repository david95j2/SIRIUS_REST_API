package com.example.sirius_restapi.mission.global.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostGlobalMissionReq {
    @NotEmpty(message = "미션이름은 필수 입력값입니다. 키가 mission_name인지 확인해주세요.")
    private String mission_name;
    @NotEmpty(message = "미션타입은 필수 입력값입니다. 키가 type인지 확인해주세요.")
    @Pattern(regexp = "^(global|local)$", message = "mission 타입은 다음과 같습니다. [global/local]")
    private String type;
}
