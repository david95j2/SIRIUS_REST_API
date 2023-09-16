package com.example.sirius_restapi.inspection.picture.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetPictureRes {
    Integer id;
    String file_name;
    String regdate;
    Float pos_x;
    Float pos_y;
    Float pos_z;
    Float roll;
    Float pitch;
    Float yaw;
}
