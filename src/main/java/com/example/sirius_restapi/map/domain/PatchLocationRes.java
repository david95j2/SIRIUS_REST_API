package com.example.sirius_restapi.map.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PatchLocationRes {
    private Integer id;
    private String location;
    private Float latitude;
    private Float longitude;
}
