package com.example.sirius_restapi.inspection.analysis;

import com.example.sirius_restapi.exception.AppException;
import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class InspectionService {

    private InspectionRepository inspectionRepository;

    public BaseResponse getInspections(Integer mapId) {
        return new BaseResponse(ErrorCode.SUCCESS,inspectionRepository.findByMapId(mapId));
    }

    public BaseResponse getInspectionById(Integer inspectionId) {
        return new BaseResponse(ErrorCode.SUCCESS,inspectionRepository.findById(inspectionId).orElseThrow(
                ()->new AppException(ErrorCode.DATA_NOT_FOUND)
        ));
    }
}
