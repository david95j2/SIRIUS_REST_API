package com.example.sirius_restapi.mission.mapping;

import com.example.sirius_restapi.exception.AppException;
import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.exception.ErrorCode;
import com.example.sirius_restapi.mission.mapping.domain.MappingMissionEntity;
import com.example.sirius_restapi.mission.mapping.domain.PostMappingWaypointReq;
import com.example.sirius_restapi.mission.mapping.domain.MappingWayPointEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MappingWaypointService {
    private MappingWaypointRepository mappingWaypointRepository;
    private MappingMissionRepository mappingMissionRepository;
    public BaseResponse getGlobalWaypoints(Integer missionId) {
        return new BaseResponse(ErrorCode.SUCCESS, mappingWaypointRepository.findAllByMissionId(missionId));
    }

    public BaseResponse getGlobalWaypointById(Integer waypointId, Integer missionId) {
        return new BaseResponse(ErrorCode.SUCCESS, mappingWaypointRepository.findByIdAndMissionId(waypointId,missionId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        ));
    }

    public BaseResponse postGlobalWaypoints(PostMappingWaypointReq postMappingWaypointReq, Integer missionId) {
        MappingMissionEntity mappingMissionEntity = mappingMissionRepository.findById(missionId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        );

        /* mission_id 와 seq 를 조회해서 seq가 중간에 들어올 시 update해야 함.*/
        mappingWaypointRepository.incrementSeqGreaterThan(missionId, postMappingWaypointReq.getSeq());

        MappingWayPointEntity mappingWayPointEntity = MappingWayPointEntity.from(postMappingWaypointReq, mappingMissionEntity);
        Integer id = mappingWaypointRepository.save(mappingWayPointEntity).getId();

        return new BaseResponse(ErrorCode.CREATED,Integer.valueOf(id)+"번 waypoint가 생성되었습니다.");
    }

    public BaseResponse deleteGlobalWayPoint(Integer waypoint_id,Integer misison_id) {
        /* mission_id 와 seq 를 조회해서 seq가 중간에 들어올 시 update해야 함.*/
        MappingWayPointEntity mappingWayPointEntity = mappingWaypointRepository.findByIdAndMissionId(waypoint_id,misison_id).orElseThrow(()->new AppException(ErrorCode.DATA_NOT_FOUND));
        mappingWaypointRepository.decrementSeqGreaterThan(mappingWayPointEntity.getMappingMissionEntity().getId(), mappingWayPointEntity.getSeq());

        mappingWaypointRepository.delete(mappingWayPointEntity);

        return new BaseResponse(ErrorCode.SUCCESS, Integer.valueOf(waypoint_id)+"번 Waypoint가 삭제되었습니다.");
    }
}
