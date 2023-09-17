package com.example.sirius_restapi.mission.local;

import com.example.sirius_restapi.exception.AppException;
import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.exception.ErrorCode;
import com.example.sirius_restapi.mission.local.domain.FittingGroupEntity;
import com.example.sirius_restapi.mission.local.domain.LocalMissionEntity;
import com.example.sirius_restapi.mission.local.domain.PatchLocalMissionRes;
import com.example.sirius_restapi.mission.local.domain.PostFittingsReq;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class LocalMissionService {
    private LocalMissionRepository localMissionRepository;
    private FittingGroupRepository fittingGroupRepository;

    public BaseResponse getLocalMissions(Integer fittingId) {
        return new BaseResponse(ErrorCode.SUCCESS, localMissionRepository.findAllByFittingId(fittingId));
    }

    public BaseResponse getLocalMissionById(Integer missionId, Integer fittingId) {
        return new BaseResponse(ErrorCode.SUCCESS, localMissionRepository.findByIdAndFittingId(missionId,fittingId));
    }

    public BaseResponse postLocalMissions(PostFittingsReq postFittingsReq, Integer fittingId) {
        FittingGroupEntity fittingGroupEntity = fittingGroupRepository.findById(fittingId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        );
        LocalMissionEntity localMissionEntity = LocalMissionEntity.from(postFittingsReq,fittingGroupEntity);
        Integer createdId = localMissionRepository.save(localMissionEntity).getId();
        return new BaseResponse(ErrorCode.CREATED, Integer.valueOf(createdId)+"번 미션이 생성되었습니다.");
    }

    public BaseResponse patchLocalMissionsById(PostFittingsReq postFittingsReq, Integer missionId, Integer fittingId) {
        LocalMissionEntity localMissionEntity = localMissionRepository.findByIdAndFittingId(missionId,fittingId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        );
        localMissionEntity.setName(postFittingsReq.getName());
        LocalMissionEntity updated = localMissionRepository.save(localMissionEntity);
        PatchLocalMissionRes patchLocalMissionRes = updated.toDto();
        return new BaseResponse(ErrorCode.ACCEPTED,patchLocalMissionRes);
    }

    @Transactional
    public BaseResponse deleteLocalMission(Integer missionId, Integer fittingId) {
        Integer deletedCount = localMissionRepository.deleteByIdAndFittingId(missionId,fittingId);
        if (deletedCount != 0) {
            return new BaseResponse(ErrorCode.SUCCESS, Integer.valueOf(missionId)+"번 미션이 삭제되었습니다.");
        } else {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
    }
}
