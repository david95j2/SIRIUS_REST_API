package com.example.sirius_restapi.inspection.analysis;

import com.example.sirius_restapi.exception.AppException;
import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.exception.ErrorCode;
import com.example.sirius_restapi.inspection.analysis.domain.InspectionEntity;
import com.example.sirius_restapi.inspection.analysis.domain.PostInspectionReq;
import com.example.sirius_restapi.map.MapRepository;
import com.example.sirius_restapi.map.domain.MapEntity;
import com.example.sirius_restapi.map.domain.MapGroupEntity;
import com.example.sirius_restapi.mission.local.LocalMissionRepository;
import com.example.sirius_restapi.mission.local.domain.LocalMissionEntity;
import com.example.sirius_restapi.mission.local.domain.PatchLocalMissionRes;
import com.example.sirius_restapi.mission.local.domain.PostLocalMissionReq;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
@Slf4j
@AllArgsConstructor
public class InspectionService {

    private InspectionRepository inspectionRepository;
    private LocalMissionRepository localMissionRepository;
    private MapRepository mapRepository;

    public BaseResponse getInspections(Integer mapId) {
        return new BaseResponse(ErrorCode.SUCCESS, inspectionRepository.findByMapId(mapId));
    }

    public BaseResponse getInspectionById(Integer inspectionId) {
        return new BaseResponse(ErrorCode.SUCCESS, inspectionRepository.findById(inspectionId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        ));
    }

    public BaseResponse postInspectAndStartFittingApp(@Valid PostInspectionReq postInspectionReq, Integer mapId) {
        MapGroupEntity mapGroupEntity = mapRepository.findMagGroupByMapId(mapId).orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        // inspection 생성
        InspectionEntity inspectionEntity = InspectionEntity.from(postInspectionReq, mapGroupEntity);
        Integer inspection_id = inspectionRepository.save(inspectionEntity).getId();

        // c++ 키기
        MapEntity mapEntity = mapRepository.findById(mapId).orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        ExecutorService localExecutorService = Executors.newSingleThreadExecutor();
        localExecutorService.execute(() -> {
            // 비동기
            ProcessBuilder processBuilder = new ProcessBuilder();
//            processBuilder.command("C:\\Users\\jylee\\Desktop\\code\\intellij\\REST_API\\bin\\test_5",mapEntity.getMapPath());
            processBuilder.command("/home/sb/workspace/pcd-manager3/build/fit_area", mapEntity.getMapPath());

            try {
                Process process = processBuilder.start();
                int exitCode = process.waitFor();
                log.info("Exited with code: " + exitCode);

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        localExecutorService.shutdown();

        return new BaseResponse(ErrorCode.CREATED, Integer.valueOf(inspection_id) + "번 inspection이 생성되었습니다.");
    }


    public BaseResponse getLocalMissions(Integer inspectionId) {
        return new BaseResponse(ErrorCode.SUCCESS, inspectionRepository.findLocalMissionAllByInspectId(inspectionId));
    }

    public BaseResponse getLocalMissionById(Integer missionId, Integer inspectionId) {
        return new BaseResponse(ErrorCode.SUCCESS, inspectionRepository.findByIdAndMissionId(inspectionId, missionId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        ));
    }

    public BaseResponse postLocalMissions(@Valid PostLocalMissionReq postLocalMissionReq, Integer inspectionId) {
        InspectionEntity inspectionEntity = inspectionRepository.findById(inspectionId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        );
        LocalMissionEntity localMissionEntity = LocalMissionEntity.from(postLocalMissionReq, inspectionEntity);
        Integer fittingGroup_id = localMissionRepository.save(localMissionEntity).getId();
        return new BaseResponse(ErrorCode.CREATED, Integer.valueOf(fittingGroup_id) + "번 미션이 생성되었습니다.");
    }

    public BaseResponse patchLocalMissions(PostLocalMissionReq postLocalMissionReq, Integer missionId, Integer inspectionId) {
        LocalMissionEntity localMissionEntity = localMissionRepository.findByIdAndInspectId(missionId, inspectionId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        );
        localMissionEntity.setName(postLocalMissionReq.getName());
        LocalMissionEntity updated = localMissionRepository.save(localMissionEntity);
        PatchLocalMissionRes patchLocalMissionRes = updated.toDto();
        return new BaseResponse(ErrorCode.ACCEPTED, patchLocalMissionRes);

    }

    @Transactional
    public BaseResponse deleteLocalMissions(Integer missionId, Integer inspectionId) {
        Integer deletedCount = localMissionRepository.deleteByIdAndInspectId(missionId, inspectionId);
        if (deletedCount != 0) {
            return new BaseResponse(ErrorCode.SUCCESS, Integer.valueOf(missionId) + "번 미션이 삭제되었습니다.");
        } else {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
    }
}
