package com.example.sirius_restapi.user;

import com.example.sirius_restapi.configuration.MyConfiguration;
import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.user.domain.DeleteUserReq;
import com.example.sirius_restapi.user.domain.PatchUserReq;
import com.example.sirius_restapi.user.domain.PostLoginReq;
import com.example.sirius_restapi.user.domain.PostUserReq;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class UserController {
    private UserService userService;
    @GetMapping("api/users")
    public BaseResponse getUsers() {
        return userService.getUsers();
    }

    @GetMapping("api/users/{login_id}")
    public BaseResponse getUserByLoginId(@PathVariable("login_id") String login_id) {
        return userService.getUserByLoginId(login_id);
    }

    @PostMapping("api/users")
    public BaseResponse postUser(@Valid @RequestBody PostUserReq postUserReq) {
        return userService.postUser(postUserReq);
    }

    @PostMapping("api/login")
    public BaseResponse login(@Valid @RequestBody PostLoginReq postLoginReq) {
        return userService.postLogin(postLoginReq);
    }

    @PatchMapping("api/users/{login_id}")
    public BaseResponse patchUserByLoginId(@PathVariable("login_id") String login_id,@Valid @RequestBody PatchUserReq patchUserReq) {
        return userService.patchUserByLoginId(patchUserReq,login_id);
    }


    @DeleteMapping("api/users/{login_id}")
    public BaseResponse deleteUserByLoginId(@PathVariable("login_id") String login_id, @Valid @RequestBody DeleteUserReq deleteUserReq) {
        return userService.deleteUserByLoginId(deleteUserReq,login_id);
    }
}
