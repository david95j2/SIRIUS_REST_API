package com.example.sirius_restapi.mission.global;

import com.example.sirius_restapi.mission.global.domain.GlobalMissionEntity;
import com.example.sirius_restapi.mission.global.domain.PatchGlobalMissionReq;
import com.example.sirius_restapi.mission.global.domain.PatchGlobalMissionRes;
import com.example.sirius_restapi.mission.global.domain.PostGlobalMissionReq;
import com.example.sirius_restapi.exception.AppException;
import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.exception.ErrorCode;
import com.example.sirius_restapi.user.UserService;
import com.example.sirius_restapi.user.domain.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class GlobalMissionService {
    private GlobalMissionRepository globalMissionRepository;
    private UserService userService;
    public BaseResponse getGlobalMissions(String login_id) {
        return new BaseResponse(ErrorCode.SUCCESS, globalMissionRepository.findAllByIdUserId(login_id));
    }

    public BaseResponse getGlobalMissionById(Integer missionId, String login_id) {
        GlobalMissionEntity globalMissionEntity = globalMissionRepository.findByIdAndUserId(missionId,login_id).orElseThrow(()->new AppException(ErrorCode.DATA_NOT_FOUND));
        return new BaseResponse(ErrorCode.SUCCESS, globalMissionEntity);
    }

    public BaseResponse postGlobalMissions(PostGlobalMissionReq postGlobalMissionReq, String login_id) {
        UserEntity userEntity = (UserEntity) userService.getUserByLoginId(login_id).getResult();
        GlobalMissionEntity globalMissionEntity = GlobalMissionEntity.from(postGlobalMissionReq,userEntity);
        Integer mission_id = globalMissionRepository.save(globalMissionEntity).getId();
        return new BaseResponse(ErrorCode.CREATED,Integer.valueOf(mission_id)+"번 미션이 생성되었습니다.");
    }

    public BaseResponse patchGlobalMissions(PatchGlobalMissionReq patchGlobalMissionReq, Integer missionId, String login_id) {
        userService.getUserByLoginId(login_id);
        GlobalMissionEntity globalMissionEntity = globalMissionRepository.findById(missionId).orElseThrow(()->new AppException(ErrorCode.DATA_NOT_FOUND));

        if (patchGlobalMissionReq.getName() != null) {
            globalMissionEntity.setName(patchGlobalMissionReq.getName());
        }

        // Save the updated entity
        GlobalMissionEntity updated = globalMissionRepository.save(globalMissionEntity);
        PatchGlobalMissionRes patchGlobalMissionRes = updated.toDto();
        return new BaseResponse(ErrorCode.ACCEPTED, patchGlobalMissionRes);
    }

    @Transactional
    public BaseResponse deleteGlobalMissions(Integer missionId, String login_id) {
        Integer deletedCount = globalMissionRepository.deleteByIdAndUserId(missionId,login_id);
        if (deletedCount != 0) {
            return new BaseResponse(ErrorCode.SUCCESS, Integer.valueOf(missionId)+"번 미션이 삭제되었습니다.");
        } else {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
    }

}
