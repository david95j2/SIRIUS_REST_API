package com.example.sirius_restapi.mission.local;

import ch.qos.logback.core.joran.action.AppenderAction;
import com.example.sirius_restapi.exception.AppException;
import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.exception.ErrorCode;
import com.example.sirius_restapi.mission.local.domain.*;
import lombok.AllArgsConstructor;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TypeService {
    private TypeRepository typeRepository;
    private LocalMissionRepository localMissionRepository;

    public BaseResponse getTypes(Integer missionId) {
        return new BaseResponse(ErrorCode.SUCCESS, typeRepository.findAllByMissionId(missionId));
    }

    public BaseResponse getTypeById(Integer typeId, Integer missionId) {
        return new BaseResponse(ErrorCode.SUCCESS, typeRepository.findByIdAndMissionId(typeId,missionId));
    }

    public BaseResponse postTypes(PostTypeReq postTypeReq, Integer missionId) {
        LocalMissionEntity localMissionEntity = localMissionRepository.findById(missionId).orElseThrow(
                ()-> new AppException(ErrorCode.DATA_NOT_FOUND)
        );
        TypeEntity typeEntity = TypeEntity.from(postTypeReq,localMissionEntity);
        Integer type_id = typeRepository.save(typeEntity).getId();
        return new BaseResponse(ErrorCode.CREATED, Integer.valueOf(type_id)+"번 type이 생성되었습니다.");
    }

    public BaseResponse patchTypesById(PatchTypeReq patchTypeReq, Integer typeId, Integer missionId) {
        TypeEntity typeEntity = typeRepository.findByIdAndMissionId(typeId,missionId).orElseThrow(
                ()-> new AppException(ErrorCode.DATA_NOT_FOUND)
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
    public BaseResponse deleteTypes(Integer typeId, Integer missionId) {
        Integer deletedCount = typeRepository.deleteByIdAndMissionId(typeId,missionId);
        if (deletedCount != 0) {
            return new BaseResponse(ErrorCode.SUCCESS,Integer.valueOf(typeId)+"번 타입이 삭제되었습니다.");
        } else {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
    }
}
