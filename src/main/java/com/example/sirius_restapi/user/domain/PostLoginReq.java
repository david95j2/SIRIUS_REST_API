package com.example.sirius_restapi.user.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostLoginReq {
    @NotBlank(message = "아이디는 필수 입력값입니다. 키가 login_id인지 확인해주세요.")
    private String login_id;
    @NotBlank(message = "비밀번호는 필수 입력값입니다. 키가 password인지 확인해주세요.")
    private String password;
}
