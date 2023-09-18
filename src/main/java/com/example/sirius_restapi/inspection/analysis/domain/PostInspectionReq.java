package com.example.sirius_restapi.inspection.analysis.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostInspectionReq {
    @NotBlank(message = "날짜는 필수 입력값입니다. 키가 date인지 확인해주세요. ex) 20230916")
    private String regdate;
    private String name;
    private Integer distance;
}
