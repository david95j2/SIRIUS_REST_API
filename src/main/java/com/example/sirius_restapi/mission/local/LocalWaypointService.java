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
    private LocalDroneRepository localDroneRepository;
    public BaseResponse getLocalWaypoints(Integer droneId) {
        return new BaseResponse(ErrorCode.SUCCESS, localWaypointRepository.findByDroneId(droneId));
    }

    public BaseResponse getLocalWaypointById(Integer waypointId, Integer droneId) {
        return new BaseResponse(ErrorCode.SUCCESS, localWaypointRepository.findByIdAndDroneId(waypointId,droneId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        ));
    }

    public BaseResponse postLocalWaypoints(PostLocalWaypointReq postLocalWaypointReq, Integer droneId) {
        LocalDroneEntity localDroneEntity = localDroneRepository.findById(droneId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        );
        /* mission_id 와 seq 를 조회해서 seq가 중간에 들어올 시 update해야 함.*/
        localWaypointRepository.incrementSeqGreaterThan(droneId, postLocalWaypointReq.getSeq());

        LocalWaypointEntity localWaypointEntity = LocalWaypointEntity.from(postLocalWaypointReq, localDroneEntity);
        Integer createdId = localWaypointRepository.save(localWaypointEntity).getId();
        return new BaseResponse(ErrorCode.CREATED,Integer.valueOf(createdId)+"번 waypoint가 생성되었습니다.");
    }

    public BaseResponse patchLocalWaypointsById(PatchLocalWaypointReq patchLocalWaypointReq, Integer waypointId, Integer droneId) {
        LocalWaypointEntity localWaypointEntity = localWaypointRepository.findByIdAndDroneId(waypointId,droneId).orElseThrow(
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
    public BaseResponse deleteLocalWaypoint(Integer waypointId, Integer droneId) {
        /* mission_id 와 seq 를 조회해서 seq가 중간에 들어올 시 update해야 함.*/
        LocalWaypointEntity localWaypointEntity = localWaypointRepository.findByIdAndDroneId(waypointId,droneId).orElseThrow(
                ()->new AppException(ErrorCode.DATA_NOT_FOUND));
        localWaypointRepository.decrementSeqGreaterThan(droneId, localWaypointEntity.getSeq());
        localWaypointRepository.delete(localWaypointEntity);
        return new BaseResponse(ErrorCode.SUCCESS,Integer.valueOf(waypointId)+"번 waypoint가 삭제되었습니다.");
    }
}
