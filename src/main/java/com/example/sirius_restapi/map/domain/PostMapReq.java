package com.example.sirius_restapi.map.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter
public class PostMapReq {
    private String file_name;
    private Integer map_count;
    private Integer map_area;
    private LocalDate date;
    private LocalTime time;
}
