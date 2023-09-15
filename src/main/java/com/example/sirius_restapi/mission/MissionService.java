package com.example.sirius_restapi.mission;

import com.example.sirius_restapi.mission.domain.MissionEntity;
import com.example.sirius_restapi.mission.domain.PatchMissionReq;
import com.example.sirius_restapi.mission.domain.PatchMissionRes;
import com.example.sirius_restapi.mission.domain.PostMissionReq;
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
public class MissionService {
    private MissionRepository missionRepository;
    private UserService userService;
    public BaseResponse getMissions(Integer userId) {
        return new BaseResponse(ErrorCode.SUCCESS,missionRepository.findAllByIdUserId(userId));
    }

    public BaseResponse getMissionById(Integer missionId, Integer userId) {
        MissionEntity missionEntity = missionRepository.findByIdAndUserId(missionId,userId).orElseThrow(()->new AppException(ErrorCode.DATA_NOT_FOUND));
        return new BaseResponse(ErrorCode.SUCCESS,missionEntity);
    }

    public BaseResponse postMissions(PostMissionReq postMissionReq, Integer userId) {
        UserEntity userEntity = (UserEntity) userService.getUserById(userId).getResult();
        MissionEntity missionEntity = MissionEntity.builder()
                .userEntity(userEntity)
                .missionName(postMissionReq.getMission_name())
                .missionType(postMissionReq.getType())
                .build();
        Integer mission_id = missionRepository.save(missionEntity).getId();
        return new BaseResponse(ErrorCode.SUCCESS,Integer.valueOf(mission_id)+"번 미션이 생성되었습니다.");
    }

    public BaseResponse patchMissions(PatchMissionReq patchMissionReq, Integer missionId, Integer userId) {
        userService.getUserById(userId);
        MissionEntity missionEntity = missionRepository.findById(missionId).orElseThrow(()->new AppException(ErrorCode.DATA_NOT_FOUND));

        if (patchMissionReq.getMission_name() != null) {
            missionEntity.setMissionName(patchMissionReq.getMission_name());
        }
        if (patchMissionReq.getType() != null) {
            missionEntity.setMissionType(patchMissionReq.getType());
        }
        // Save the updated entity
        MissionEntity updated = missionRepository.save(missionEntity);
        PatchMissionRes patchMissionRes = updated.toDto();
        return new BaseResponse(ErrorCode.SUCCESS,patchMissionRes);
    }

    @Transactional
    public BaseResponse deleteMissions(Integer missionId, Integer userId) {
        Integer deletedCount = missionRepository.deleteByIdAndUserId(missionId,userId);
        if (deletedCount != 0) {
            return new BaseResponse(ErrorCode.SUCCESS, Integer.valueOf(missionId)+"번 미션이 삭제되었습니다.");
        } else {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
    }

}
