package com.example.sirius_restapi.inspection.analysis;

import com.example.sirius_restapi.exception.AppException;
import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.exception.ErrorCode;
import com.example.sirius_restapi.inspection.analysis.domain.AnalysisEntity;
import com.example.sirius_restapi.inspection.analysis.domain.GetAnalysesRes;
import com.example.sirius_restapi.inspection.analysis.domain.InspectionEntity;
import com.example.sirius_restapi.inspection.analysis.domain.PostInspectionReq;
import com.example.sirius_restapi.inspection.picture.PictureService;
import com.example.sirius_restapi.inspection.picture.domain.GetPictureRes;
import com.example.sirius_restapi.map.MapRepository;
import com.example.sirius_restapi.map.domain.MapEntity;
import com.example.sirius_restapi.map.domain.MapGroupEntity;
import com.example.sirius_restapi.mission.plan.LocalMissionRepository;
import com.example.sirius_restapi.mission.plan.domain.PlansEntity;
import com.example.sirius_restapi.mission.plan.domain.PatchLocalMissionRes;
import com.example.sirius_restapi.mission.plan.domain.PostLocalMissionReq;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


@Service
@Slf4j
@AllArgsConstructor
public class InspectionService {

    private InspectionRepository inspectionRepository;
    private AnalysisRepository analysisRepository;
    private LocalMissionRepository localMissionRepository;
    private MapRepository mapRepository;
    private PictureService pictureService;

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
        PlansEntity plansEntity = PlansEntity.from(postLocalMissionReq, inspectionEntity);
        Integer fittingGroup_id = localMissionRepository.save(plansEntity).getId();
        return new BaseResponse(ErrorCode.CREATED, Integer.valueOf(fittingGroup_id) + "번 미션이 생성되었습니다.");
    }

    public BaseResponse patchLocalMissions(PostLocalMissionReq postLocalMissionReq, Integer missionId, Integer inspectionId) {
        PlansEntity plansEntity = localMissionRepository.findByIdAndInspectId(missionId, inspectionId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        );
        plansEntity.setName(postLocalMissionReq.getName());
        PlansEntity updated = localMissionRepository.save(plansEntity);
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

    public BaseResponse getAnalyses(Integer inspectionId) {
        List<AnalysisEntity> results = analysisRepository.findByInspectId(inspectionId);
        List<GetAnalysesRes> new_results = results.stream().map(x -> {
            GetAnalysesRes getAnalysesRes = new GetAnalysesRes();
            getAnalysesRes.setId(x.getId());
            if (x.getStatus() == 1) {
                getAnalysesRes.setStatus("완료");
            } else {
                getAnalysesRes.setStatus("진행중");
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = x.getRegdate().format(formatter);
            getAnalysesRes.setRegdate(formattedDate);
            getAnalysesRes.setAi_type(x.getAiType());
            return getAnalysesRes;
        }).collect(Collectors.toList());
        return new BaseResponse(ErrorCode.SUCCESS, new_results);
    }

    public BaseResponse getAnalysisById(Integer analysisId, Integer inspectionId) {
        AnalysisEntity analysisEntity = analysisRepository.findByIdAndInspectId(analysisId,inspectionId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        );
        GetAnalysesRes getAnalysesRes = analysisEntity.toDto();
        return new BaseResponse(ErrorCode.SUCCESS, getAnalysesRes);
    }

    public BaseResponse postAnalysisById(Integer analysisId, Integer inspectionId) {
        // inspection(원본 이미지)가 존재하는지 검사
        List<GetPictureRes> results = (List<GetPictureRes>) pictureService.getPicturesByInspectId(inspectionId,null,null).getResult();
        if (results.isEmpty()) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }

        // analyses id 최대값 조회
        Integer max_analysisId = analysisRepository.findMaxId(inspectionId);
        if (max_analysisId == null) {
            max_analysisId = 1;
        } else {
            max_analysisId = max_analysisId + 1;
        }
        System.out.println("???????????");
        System.out.println(results.get(1).getFile_name());
        // python 검사 시작
//        ExecutorService localExecutorService = Executors.newSingleThreadExecutor();
//        localExecutorService.execute(() -> {
//            ProcessBuilder processBuilder = new ProcessBuilder();
//            // 쉘 스크립트로 실행
//            processBuilder.command("/home/sb/Desktop/vsc/0926koceti/20230901_mmsegmentation/inferences/inference_and_quantification_mmseg.py");
//
//            try {
//                Process process = processBuilder.start();
//                int exitCode = process.waitFor();
//                log.info("Exited with code: " + exitCode);
//
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        localExecutorService.shutdown();
        return new BaseResponse(ErrorCode.SUCCESS);
    }
}
