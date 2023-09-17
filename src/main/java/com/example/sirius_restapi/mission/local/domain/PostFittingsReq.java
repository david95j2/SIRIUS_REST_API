package com.example.sirius_restapi.mission.local.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostFittingsReq {
    @NotBlank(message = "name은 필수 입력값입니다. 키가 name인지 확인해주세요.")
    private String name;
}
