package com.example.sirius_restapi.mission.plan;

import com.example.sirius_restapi.exception.AppException;
import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.exception.ErrorCode;
import com.example.sirius_restapi.mission.plan.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TypeService {
    private TypeRepository typeRepository;
    private LocalWaypointRepository localWaypointRepository;

    public BaseResponse getTypes(Integer waypoinId) {
        return new BaseResponse(ErrorCode.SUCCESS, typeRepository.findAllByWaypointId(waypoinId));
    }

    public BaseResponse getTypeById(Integer typeId, Integer waypointId) {
        return new BaseResponse(ErrorCode.SUCCESS, typeRepository.findByIdAndWaypointId(typeId, waypointId));
    }

    public BaseResponse postTypes(PostTypeReq postTypeReq, Integer waypointId) {
        LocalWaypointEntity localWaypointEntity = localWaypointRepository.findById(waypointId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        );
        // waypoint에 type이 이미 있으면 patch
        TypeEntity comparedType = typeRepository.findByWayPointId(waypointId).orElse(null);
        if (comparedType != null) {
            throw new AppException(ErrorCode.DUPLICATED_DATA);
        }
        TypeEntity typeEntity = TypeEntity.from(postTypeReq, localWaypointEntity);
        Integer type_id = typeRepository.save(typeEntity).getId();
        return new BaseResponse(ErrorCode.CREATED, Integer.valueOf(type_id) + "번 type이 생성되었습니다.");
    }

    public BaseResponse patchTypesById(PatchTypeReq patchTypeReq, Integer typeId, Integer waypoint_id) {
        TypeEntity typeEntity = typeRepository.findByIdAndWaypointId(typeId, waypoint_id).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        );
        if (patchTypeReq.getType() != null) {
            typeEntity.setType(patchTypeReq.getType());
        }
        if (patchTypeReq.getLine_auto() != null) {
            typeEntity.setLineAuto(patchTypeReq.getLine_auto());
        }
        if (patchTypeReq.getLine_direction() != null) {
            typeEntity.setLineDirection(patchTypeReq.getLine_direction());
        }
        if (patchTypeReq.getCircle_inward() != null) {
            typeEntity.setCircleInward(patchTypeReq.getCircle_inward());
        }
        if (patchTypeReq.getCircle_start_angle() != null) {
            typeEntity.setCircleStartAngle(patchTypeReq.getCircle_start_angle());
        }
        if (patchTypeReq.getRect_inward() != null) {
            typeEntity.setRectInward(patchTypeReq.getRect_inward());
        }
        if (patchTypeReq.getBottom_auto() != null) {
            typeEntity.setBottomAuto(patchTypeReq.getBottom_auto());
        }
        if (patchTypeReq.getBottom_whole() != null) {
            typeEntity.setBottomWhole(patchTypeReq.getBottom_whole());
        }
        TypeEntity updated = typeRepository.save(typeEntity);
        PatchTypeRes patchTypeRes = updated.toDto();
        return new BaseResponse(ErrorCode.ACCEPTED, patchTypeRes);
    }

    @Transactional
    public BaseResponse deleteTypes(Integer typeId, Integer waypointId) {
        Integer deletedCount = typeRepository.deleteByIdAndWaypointId(typeId, waypointId);
        if (deletedCount != 0) {
            return new BaseResponse(ErrorCode.SUCCESS, Integer.valueOf(typeId) + "번 타입이 삭제되었습니다.");
        } else {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
    }
}
