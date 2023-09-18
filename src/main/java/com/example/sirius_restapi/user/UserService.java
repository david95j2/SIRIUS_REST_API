package com.example.sirius_restapi.user;

import com.example.sirius_restapi.exception.AppException;
import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.exception.ErrorCode;
import com.example.sirius_restapi.user.domain.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public BaseResponse getUsers() {
        List<UserEntity> results = userRepository.findAll();
        return new BaseResponse(ErrorCode.SUCCESS,results);
    }

    public BaseResponse getUserByLoginId(String loginId) {
        UserEntity result = userRepository.findByLoginId(loginId).orElseThrow(()->new AppException(ErrorCode.NOT_FOUND));
        return new BaseResponse(ErrorCode.SUCCESS,result);
    }

    public BaseResponse postUser(PostUserReq postUserReq) {
        if (postUserReq.getAuthority().equals("admin")) {
            UserEntity origin_user = userRepository.findByAuthority(postUserReq.getAuthority()).orElse(null);
            if (origin_user != null) {
                throw new AppException(ErrorCode.ADMIN_CREATED_FORBIDDEN_ACCESS);
            }
        }

        UserEntity userEntity = UserEntity.from(postUserReq);
        Integer user_id = userRepository.save(userEntity).getId();
        return new BaseResponse(ErrorCode.CREATED,String.valueOf(user_id)+"번 유저가 생성되었습니다.");
    }

    public BaseResponse postLogin(PostLoginReq postLoginReq) {
        // 유저 있는지 확인
        userRepository.findByLoginIdAndPassword(postLoginReq.getLogin_id(),postLoginReq.getPassword())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        return new BaseResponse(ErrorCode.SUCCESS, "로그인에 성공하였습니다.");
    }

    public BaseResponse patchUserByLoginId(PatchUserReq patchUserReq, String login_id) {
        // 유저 있는지 확인
        UserEntity userEntity = userRepository.findByLoginId(login_id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        // 비밀번호 확인
        if (!patchUserReq.getPassword().equals(userEntity.getPassword())) {
            throw new AppException(ErrorCode.INCORRECT);
        }
        userEntity.updatePassword(patchUserReq.getNewPassword());

        userRepository.save(userEntity);

        return new BaseResponse(ErrorCode.ACCEPTED,login_id+" 유저의 비밀번호가 변경되었습니다.");
    }

    @Transactional
    public BaseResponse deleteUserByLoginId(@Valid DeleteUserReq deleteUserReq, String loginId) {
        // 유저 있는지 확인
        userRepository.findByLoginId(loginId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        // 비밀번호 확인
        UserEntity userEntity = userRepository.findByPassword(deleteUserReq.getPassword()).orElseThrow(
                ()-> new AppException(ErrorCode.INCORRECT));

        userRepository.deleteUserByLoginId(loginId);

        return new BaseResponse(ErrorCode.SUCCESS,"유저가 삭제되었습니다.");
    }
}
