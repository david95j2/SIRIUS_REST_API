package com.example.sirius_restapi.mission.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PatchMissionReq {
    private String mission_name;
    private String type;
}
