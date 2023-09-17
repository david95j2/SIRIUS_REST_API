package com.example.sirius_restapi.user;

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

    @GetMapping("api/users/{user_id}")
    public BaseResponse getUserById(@PathVariable Integer user_id) {
        return userService.getUserById(user_id);
    }

    @PostMapping("api/users")
    public BaseResponse postUser(@Valid @RequestBody PostUserReq postUserReq) {
        return userService.postUser(postUserReq);
    }

    @PostMapping("api/login")
    public BaseResponse login(@Valid @RequestBody PostLoginReq postLoginReq) {
        return userService.postLogin(postLoginReq);
    }

    @PatchMapping("api/users/{user_id}")
    public BaseResponse patchUserById(@PathVariable Integer user_id,@Valid @RequestBody PatchUserReq patchUserReq) {
        return userService.patchUserById(patchUserReq,user_id);
    }


    @DeleteMapping("api/users/{user_id}")
    public BaseResponse deleteUserById(@PathVariable Integer user_id, @Valid @RequestBody DeleteUserReq deleteUserReq) {
        return userService.deleteUserById(deleteUserReq,user_id);
    }
}
