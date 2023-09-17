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
    public BaseResponse getGlobalMissions(Integer userId) {
        return new BaseResponse(ErrorCode.SUCCESS, globalMissionRepository.findAllByIdUserId(userId));
    }

    public BaseResponse getGlobalMissionById(Integer missionId, Integer userId) {
        GlobalMissionEntity globalMissionEntity = globalMissionRepository.findByIdAndUserId(missionId,userId).orElseThrow(()->new AppException(ErrorCode.DATA_NOT_FOUND));
        return new BaseResponse(ErrorCode.SUCCESS, globalMissionEntity);
    }

    public BaseResponse postGlobalMissions(PostGlobalMissionReq postGlobalMissionReq, Integer userId) {
        UserEntity userEntity = (UserEntity) userService.getUserById(userId).getResult();
        GlobalMissionEntity globalMissionEntity = GlobalMissionEntity.builder()
                .userEntity(userEntity)
                .missionName(postGlobalMissionReq.getMission_name())
                .missionType(postGlobalMissionReq.getType())
                .build();
        Integer mission_id = globalMissionRepository.save(globalMissionEntity).getId();
        return new BaseResponse(ErrorCode.CREATED,Integer.valueOf(mission_id)+"번 미션이 생성되었습니다.");
    }

    public BaseResponse patchGlobalMissions(PatchGlobalMissionReq patchGlobalMissionReq, Integer missionId, Integer userId) {
        userService.getUserById(userId);
        GlobalMissionEntity globalMissionEntity = globalMissionRepository.findById(missionId).orElseThrow(()->new AppException(ErrorCode.DATA_NOT_FOUND));

        if (patchGlobalMissionReq.getMission_name() != null) {
            globalMissionEntity.setMissionName(patchGlobalMissionReq.getMission_name());
        }
        if (patchGlobalMissionReq.getType() != null) {
            globalMissionEntity.setMissionType(patchGlobalMissionReq.getType());
        }
        // Save the updated entity
        GlobalMissionEntity updated = globalMissionRepository.save(globalMissionEntity);
        PatchGlobalMissionRes patchGlobalMissionRes = updated.toDto();
        return new BaseResponse(ErrorCode.ACCEPTED, patchGlobalMissionRes);
    }

    @Transactional
    public BaseResponse deleteGlobalMissions(Integer missionId, Integer userId) {
        Integer deletedCount = globalMissionRepository.deleteByIdAndUserId(missionId,userId);
        if (deletedCount != 0) {
            return new BaseResponse(ErrorCode.SUCCESS, Integer.valueOf(missionId)+"번 미션이 삭제되었습니다.");
        } else {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
    }

}
