package com.example.sirius_restapi.mission.mapping.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostMappingMissionReq {
    @NotBlank(message = "미션이름은 필수 입력값입니다. 키가 name인지 확인해주세요.")
    private String name;
}
