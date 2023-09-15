package com.example.sirius_restapi.mission.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PatchMissionRes {
    private Integer id;
    private String mission_name;
    private String type;
}
