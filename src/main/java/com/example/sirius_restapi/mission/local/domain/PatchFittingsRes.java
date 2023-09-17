package com.example.sirius_restapi.mission.local.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PatchFittingsRes {
    private Integer id;
    private String name;
    private String regdate;
}
