package com.example.sirius_restapi.drone.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PatchDroneReq {
    private Float min;
    private Float max;
    private String name;
    private Integer x_dimension;
    private Integer y_dimension;
    private Integer z_dimension;
}
