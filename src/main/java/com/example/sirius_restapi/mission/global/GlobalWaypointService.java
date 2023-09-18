package com.example.sirius_restapi.mission.global;

import com.example.sirius_restapi.exception.AppException;
import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.exception.ErrorCode;
import com.example.sirius_restapi.mission.global.domain.GlobalMissionEntity;
import com.example.sirius_restapi.mission.global.domain.PostGlobalWaypointReq;
import com.example.sirius_restapi.mission.global.domain.GlobalWayPointEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GlobalWaypointService {
    private GlobalWaypointRepository globalWaypointRepository;
    private GlobalMissionRepository globalMissionRepository;
    public BaseResponse getGlobalWaypoints(Integer missionId) {
        return new BaseResponse(ErrorCode.SUCCESS, globalWaypointRepository.findAllByMissionId(missionId));
    }

    public BaseResponse getGlobalWaypointById(Integer waypointId, Integer missionId) {
        return new BaseResponse(ErrorCode.SUCCESS, globalWaypointRepository.findByIdAndMissionId(waypointId,missionId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        ));
    }

    public BaseResponse postGlobalWaypoints(PostGlobalWaypointReq postGlobalWaypointReq, Integer missionId) {
        GlobalMissionEntity globalMissionEntity = globalMissionRepository.findById(missionId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        );

        /* mission_id 와 seq 를 조회해서 seq가 중간에 들어올 시 update해야 함.*/
        globalWaypointRepository.incrementSeqGreaterThan(missionId, postGlobalWaypointReq.getSeq());

        GlobalWayPointEntity globalWayPointEntity = GlobalWayPointEntity.from(postGlobalWaypointReq, globalMissionEntity);
        Integer id = globalWaypointRepository.save(globalWayPointEntity).getId();

        return new BaseResponse(ErrorCode.CREATED,Integer.valueOf(id)+"번 waypoint가 생성되었습니다.");
    }

    public BaseResponse deleteGlobalWayPoint(Integer waypoint_id,Integer misison_id) {
        /* mission_id 와 seq 를 조회해서 seq가 중간에 들어올 시 update해야 함.*/
        GlobalWayPointEntity globalWayPointEntity = globalWaypointRepository.findByIdAndMissionId(waypoint_id,misison_id).orElseThrow(()->new AppException(ErrorCode.DATA_NOT_FOUND));
        globalWaypointRepository.decrementSeqGreaterThan(globalWayPointEntity.getGlobalMissionEntity().getId(), globalWayPointEntity.getSeq());

        globalWaypointRepository.delete(globalWayPointEntity);

        return new BaseResponse(ErrorCode.SUCCESS, Integer.valueOf(waypoint_id)+"번 Waypoint가 삭제되었습니다.");
    }
}
