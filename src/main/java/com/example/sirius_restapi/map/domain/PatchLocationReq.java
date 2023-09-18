package com.example.sirius_restapi.map.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PatchLocationReq {
    private String location;
    private Float latitude;
    private Float longitude;
}
