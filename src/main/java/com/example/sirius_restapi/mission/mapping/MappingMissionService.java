package com.example.sirius_restapi.mission.mapping;

import com.example.sirius_restapi.mission.mapping.domain.MappingMissionEntity;
import com.example.sirius_restapi.mission.mapping.domain.PatchMappingMissionReq;
import com.example.sirius_restapi.mission.mapping.domain.PatchMappingMissionRes;
import com.example.sirius_restapi.mission.mapping.domain.PostMappingMissionReq;
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
public class MappingMissionService {
    private MappingMissionRepository mappingMissionRepository;
    private UserService userService;
    public BaseResponse getGlobalMissions(String login_id) {
        return new BaseResponse(ErrorCode.SUCCESS, mappingMissionRepository.findAllByIdUserId(login_id));
    }

    public BaseResponse getGlobalMissionById(Integer missionId, String login_id) {
        MappingMissionEntity mappingMissionEntity = mappingMissionRepository.findByIdAndUserId(missionId,login_id).orElseThrow(()->new AppException(ErrorCode.DATA_NOT_FOUND));
        return new BaseResponse(ErrorCode.SUCCESS, mappingMissionEntity);
    }

    public BaseResponse postGlobalMissions(PostMappingMissionReq postMappingMissionReq, String login_id) {
        UserEntity userEntity = (UserEntity) userService.getUserByLoginId(login_id).getResult();
        MappingMissionEntity mappingMissionEntity = MappingMissionEntity.from(postMappingMissionReq,userEntity);
        Integer mission_id = mappingMissionRepository.save(mappingMissionEntity).getId();
        return new BaseResponse(ErrorCode.CREATED,Integer.valueOf(mission_id)+"번 미션이 생성되었습니다.");
    }

    public BaseResponse patchGlobalMissions(PatchMappingMissionReq patchMappingMissionReq, Integer missionId, String login_id) {
        userService.getUserByLoginId(login_id);
        MappingMissionEntity mappingMissionEntity = mappingMissionRepository.findById(missionId).orElseThrow(()->new AppException(ErrorCode.DATA_NOT_FOUND));

        if (patchMappingMissionReq.getName() != null) {
            mappingMissionEntity.setName(patchMappingMissionReq.getName());
        }

        // Save the updated entity
        MappingMissionEntity updated = mappingMissionRepository.save(mappingMissionEntity);
        PatchMappingMissionRes patchMappingMissionRes = updated.toDto();
        return new BaseResponse(ErrorCode.ACCEPTED, patchMappingMissionRes);
    }

    @Transactional
    public BaseResponse deleteGlobalMissions(Integer missionId, String login_id) {
        Integer deletedCount = mappingMissionRepository.deleteByIdAndUserId(missionId,login_id);
        if (deletedCount != 0) {
            return new BaseResponse(ErrorCode.SUCCESS, Integer.valueOf(missionId)+"번 미션이 삭제되었습니다.");
        } else {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
    }

}
