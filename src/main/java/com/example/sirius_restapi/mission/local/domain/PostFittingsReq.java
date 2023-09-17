package com.example.sirius_restapi.mission.local.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostFittingsReq {
    @NotEmpty(message = "이름은 필수 입력값입니다. 키가 name인지 확인해주세요.")
    private String name;
}
