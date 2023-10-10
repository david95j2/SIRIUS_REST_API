package com.example.sirius_restapi.mission.plan;

import com.example.sirius_restapi.exception.AppException;
import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.exception.ErrorCode;
import com.example.sirius_restapi.mission.plan.domain.PlansEntity;
import com.example.sirius_restapi.mission.plan.domain.LocalDroneEntity;
import com.example.sirius_restapi.mission.plan.domain.PatchLocalDroneRes;
import com.example.sirius_restapi.mission.plan.domain.PostLocalMissionReq;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class LocalDroneService {
    private LocalDroneRepository localDroneRepository;
    private LocalWaypointRepository localWaypointRepository;
    private LocalMissionRepository localMissionRepository;

    public BaseResponse getLocalDrones(Integer missionId) {
        return new BaseResponse(ErrorCode.SUCCESS, localDroneRepository.findAllByMissionId(missionId));
    }

    public BaseResponse getLocalDroneById(Integer droneId, Integer missionId) {
        return new BaseResponse(ErrorCode.SUCCESS, localDroneRepository.findByIdAndMissionId(droneId, missionId));
    }

    public BaseResponse postLocalDrones(PostLocalMissionReq postLocalMissionReq, Integer missionId) {
        PlansEntity plansEntity = localMissionRepository.findById(missionId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        );
        LocalDroneEntity localDroneEntity = LocalDroneEntity.from(postLocalMissionReq, plansEntity);
        Integer createdId = localDroneRepository.save(localDroneEntity).getId();
        return new BaseResponse(ErrorCode.CREATED, Integer.valueOf(createdId) + "번 드론이 생성되었습니다.");
    }

    public BaseResponse patchLocalDrones(PostLocalMissionReq postLocalMissionReq, Integer droneId, Integer missionId) {
        LocalDroneEntity localDroneEntity = localDroneRepository.findByIdAndMissionId(droneId, missionId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        );
        localDroneEntity.setName(postLocalMissionReq.getName());
        LocalDroneEntity updated = localDroneRepository.save(localDroneEntity);
        PatchLocalDroneRes patchLocalDroneRes = updated.toDto();
        return new BaseResponse(ErrorCode.ACCEPTED, patchLocalDroneRes);
    }

    //    @Transactional
//    public BaseResponse deleteLocalMission(Integer missionId, Integer fittingId) {
//        Integer deletedCount = localMissionRepository.deleteByIdAndFittingId(missionId,fittingId);
//        if (deletedCount != 0) {
//            return new BaseResponse(ErrorCode.SUCCESS, Integer.valueOf(missionId)+"번 미션이 삭제되었습니다.");
//        } else {
//            throw new AppException(ErrorCode.DATA_NOT_FOUND);
//        }
//    }
    @Transactional
    public BaseResponse deleteLocalDrones(Integer droneId, Integer missionId) {
        LocalDroneEntity mission = localDroneRepository.findById(droneId).orElseThrow(()->new AppException(ErrorCode.DATA_NOT_FOUND));
        if (mission != null) {
            // 먼저 연관된 LocalWaypointEntity 항목들을 삭제
            localWaypointRepository.deleteAll(mission.getLocalWaypointEntities());

            // 이후 LocalMissionEntity 삭제
            localDroneRepository.delete(mission);
            return new BaseResponse(ErrorCode.SUCCESS, Integer.valueOf(droneId) + "번 드론이 삭제되었습니다.");
        } else {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
    }

}
