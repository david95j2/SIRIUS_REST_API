package com.example.sirius_restapi.user.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PatchUserReq {
    @NotEmpty(message = "비밀번호는 필수 입력값입니다. 키가 password인지 확인해주세요.")
    private String password;
    @NotEmpty(message = "비밀번호 확인은 필수입니다. 키가 newPassword인지 확인해주세요.")
    private String newPassword;
}
