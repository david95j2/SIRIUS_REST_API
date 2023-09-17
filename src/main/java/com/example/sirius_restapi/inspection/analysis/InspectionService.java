package com.example.sirius_restapi.inspection.analysis;

import com.example.sirius_restapi.exception.AppException;
import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.exception.ErrorCode;
import com.example.sirius_restapi.inspection.analysis.domain.InspectionEntity;
import com.example.sirius_restapi.inspection.analysis.domain.PostInspectionReq;
import com.example.sirius_restapi.map.MapRepository;
import com.example.sirius_restapi.map.domain.MapGroupEntity;
import com.example.sirius_restapi.mission.local.FittingGroupRepository;
import com.example.sirius_restapi.mission.local.domain.FittingGroupEntity;
import com.example.sirius_restapi.mission.local.domain.PatchFittingsRes;
import com.example.sirius_restapi.mission.local.domain.PostFittingsReq;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@AllArgsConstructor
public class InspectionService {

    private InspectionRepository inspectionRepository;
    private FittingGroupRepository fittingGroupRepository;
    private MapRepository mapRepository;

    public BaseResponse getInspections(Integer mapId) {
        return new BaseResponse(ErrorCode.SUCCESS,inspectionRepository.findByMapId(mapId));
    }

    public BaseResponse getInspectionById(Integer inspectionId) {
        return new BaseResponse(ErrorCode.SUCCESS,inspectionRepository.findById(inspectionId).orElseThrow(
                ()->new AppException(ErrorCode.DATA_NOT_FOUND)
        ));
    }

    public BaseResponse postInspectAndStartFittingApp(@Valid PostInspectionReq postInspectionReq, Integer mapId) {
        MapGroupEntity mapGroupEntity = mapRepository.findMagGroupByMapId(mapId).orElseThrow(()->new AppException(ErrorCode.DATA_NOT_FOUND));

        // inspection 생성
        InspectionEntity inspectionEntity = InspectionEntity.from(postInspectionReq,mapGroupEntity);
        Integer inspection_id = inspectionRepository.save(inspectionEntity).getId();

        // c++ 키기

        return new BaseResponse(ErrorCode.CREATED,Integer.valueOf(inspection_id)+"번 inspection이 생성되었습니다.");
    }


    public BaseResponse getFittingGroups(Integer inspectionId) {
        return new BaseResponse(ErrorCode.SUCCESS,inspectionRepository.findFittingsAllByInspectId(inspectionId));
    }

    public BaseResponse getFittingGroupById(Integer fittingId, Integer inspectionId) {
        return new BaseResponse(ErrorCode.SUCCESS,inspectionRepository.findByIdAndFittingId(inspectionId,fittingId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        ));
    }

    public BaseResponse postFittingGroups(@Valid PostFittingsReq postFittingsReq, Integer inspectionId) {
        InspectionEntity inspectionEntity = inspectionRepository.findById(inspectionId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        );
        FittingGroupEntity fittingGroupEntity = FittingGroupEntity.from(postFittingsReq,inspectionEntity);
        Integer fittingGroup_id = fittingGroupRepository.save(fittingGroupEntity).getId();
        return new BaseResponse(ErrorCode.CREATED, Integer.valueOf(fittingGroup_id)+"번 Fitting Group이 생성되었습니다.");
    }

    public BaseResponse patchFittingGroupById(PostFittingsReq postFittingsReq, Integer fittingId, Integer inspectionId) {
        FittingGroupEntity fittingGroupEntity = fittingGroupRepository.findByIdAndInspectId(fittingId,inspectionId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        );
        fittingGroupEntity.setName(postFittingsReq.getName());
        FittingGroupEntity updated = fittingGroupRepository.save(fittingGroupEntity);
        PatchFittingsRes patchFittingsRes = updated.toDto();
        return new BaseResponse(ErrorCode.ACCEPTED,patchFittingsRes);

    }
    @Transactional
    public BaseResponse deleteFittingGroupById(Integer fittingId, Integer inspectionId) {
        Integer deletedCount = fittingGroupRepository.deleteByIdAndInspectId(fittingId,inspectionId);
        if (deletedCount != 0) {
            return new BaseResponse(ErrorCode.SUCCESS, Integer.valueOf(fittingId)+"번 그룹이 삭제되었습니다.");
        } else {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
    }
}
