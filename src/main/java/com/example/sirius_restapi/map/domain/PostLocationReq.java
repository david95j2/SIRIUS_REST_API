package com.example.sirius_restapi.map.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;


@Getter @Setter
public class PostLocationReq {
    @NotBlank(message = "장소는 필수 입력값입니다. 키가 location인지 확인해주세요.")
    private String location;
    @NotNull(message = "위도는 필수 입력값입니다. 키가 laititude인지 확인해주세요.")
    private Float latitude;
    @NotNull(message = "경도는 필수 입력값입니다. 키가 longitude읹지 확인해주세요.")
    private Float longitude;

    public static PostLocationReq fromJSONObject(JSONObject jsonObject) {
        PostLocationReq request = new PostLocationReq();

        if (jsonObject.get("location") != null) {
            request.setLocation((String) jsonObject.get("location"));
        }

        if (jsonObject.get("latitude") != null) {
            request.setLatitude(Float.parseFloat(jsonObject.get("latitude").toString()));
        }

        if (jsonObject.get("longitude") != null) {
            request.setLongitude(Float.parseFloat(jsonObject.get("longitude").toString()));
        }

        return request;
    }
}
