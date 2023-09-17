package com.example.sirius_restapi.mission.local;

import com.example.sirius_restapi.exception.AppException;
import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.exception.ErrorCode;
import com.example.sirius_restapi.mission.local.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class LocalWaypointService {
    private LocalWaypointRepository localWaypointRepository;
    private LocalMissionRepository localMissionRepository;
    public BaseResponse getLocalWaypoints(Integer missionId) {
        return new BaseResponse(ErrorCode.SUCCESS, localWaypointRepository.findByMissionId(missionId));
    }

    public BaseResponse getLocalWaypointById(Integer waypointId, Integer missionId) {
        return new BaseResponse(ErrorCode.SUCCESS, localWaypointRepository.findByIdAndMissionId(waypointId,missionId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        ));
    }

    public BaseResponse postLocalWaypoints(PostLocalWaypointReq postLocalWaypointReq, Integer missionId) {
        LocalMissionEntity localMissionEntity = localMissionRepository.findById(missionId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        );
        /* mission_id 와 seq 를 조회해서 seq가 중간에 들어올 시 update해야 함.*/
        localWaypointRepository.incrementSeqGreaterThan(missionId, postLocalWaypointReq.getSeq());

        LocalWaypointEntity localWaypointEntity = LocalWaypointEntity.from(postLocalWaypointReq,localMissionEntity);
        Integer createdId = localWaypointRepository.save(localWaypointEntity).getId();
        return new BaseResponse(ErrorCode.CREATED,Integer.valueOf(createdId)+"번 waypoint가 생성되었습니다.");
    }

    public BaseResponse patchLocalWaypointsById(PatchLocalWaypointReq patchLocalWaypointReq, Integer waypointId, Integer missionId) {
        LocalWaypointEntity localWaypointEntity = localWaypointRepository.findByIdAndMissionId(waypointId,missionId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        );
        if (patchLocalWaypointReq.getSeq() != null) {
            localWaypointEntity.setSeq(patchLocalWaypointReq.getSeq());
        }
        if (patchLocalWaypointReq.getPos_x() != null) {
            localWaypointEntity.setPosX(patchLocalWaypointReq.getPos_x());
        }
        if (patchLocalWaypointReq.getPos_y() != null) {
            localWaypointEntity.setPosY(patchLocalWaypointReq.getPos_y());
        }
        if (patchLocalWaypointReq.getPos_z() != null) {
            localWaypointEntity.setPosZ(patchLocalWaypointReq.getPos_z());
        }
        if (patchLocalWaypointReq.getYaw() != null) {
            localWaypointEntity.setYaw(patchLocalWaypointReq.getYaw());
        }
        if (patchLocalWaypointReq.getChecked() != null) {
            localWaypointEntity.setChecked(patchLocalWaypointReq.getChecked());
        }
        if (patchLocalWaypointReq.getCompleted() != null) {
            localWaypointEntity.setCompleted(patchLocalWaypointReq.getCompleted());
        }
        if (patchLocalWaypointReq.getGroup_num() != null) {
            localWaypointEntity.setGroupNum(patchLocalWaypointReq.getGroup_num());
        }
        LocalWaypointEntity updated = localWaypointRepository.save(localWaypointEntity);
        PatchLocalWaypointRes patchLocalWaypointRes = updated.toDto();
        return new BaseResponse(ErrorCode.ACCEPTED, patchLocalWaypointRes);
    }

    @Transactional
    public BaseResponse deleteLocalWaypoint(Integer waypointId, Integer missionId) {
        /* mission_id 와 seq 를 조회해서 seq가 중간에 들어올 시 update해야 함.*/
        LocalWaypointEntity localWaypointEntity = localWaypointRepository.findByIdAndMissionId(waypointId,missionId).orElseThrow(
                ()->new AppException(ErrorCode.DATA_NOT_FOUND));
        localWaypointRepository.decrementSeqGreaterThan(missionId, localWaypointEntity.getSeq());
        localWaypointRepository.delete(localWaypointEntity);
        return new BaseResponse(ErrorCode.SUCCESS,Integer.valueOf(waypointId)+"번 waypoint가 삭제되었습니다.");
    }
}
