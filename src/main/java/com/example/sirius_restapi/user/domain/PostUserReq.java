package com.example.sirius_restapi.user.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostUserReq {
    @NotEmpty(message = "아이디는 필수 입력값입니다. 키가 login_id인지 확인해주세요.")
    private String login_id;
    @NotEmpty(message = "비밀번호는 필수 입력값입니다. 키가 password인지 확인해주세요.")
    private String password;
    @NotEmpty(message = "유저별 권한은 필수 입력값입니다. 키가 authority인지 확인해주세요.")
    @Pattern(regexp = "^(admin|user|editor)$", message = "유저별 권한은 다음과 같습니다. [admin/user/editor]")
    private String authority;
}
