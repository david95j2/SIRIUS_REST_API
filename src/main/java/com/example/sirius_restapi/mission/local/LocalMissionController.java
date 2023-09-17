package com.example.sirius_restapi.mission.local;

import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.inspection.analysis.InspectionService;
import com.example.sirius_restapi.mission.local.domain.PatchFittingsRes;
import com.example.sirius_restapi.mission.local.domain.PostFittingsReq;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class LocalMissionController {
    private LocalMissionService localMissionService;
    private InspectionService inspectionService;

    @GetMapping("api/report/maps/inspections/{inspection_id}/fittings")
    public BaseResponse getFittingGroups(@PathVariable Integer inspection_id) {
        return inspectionService.getFittingGroups(inspection_id);
    }

    @GetMapping("api/report/maps/inspections/{inspection_id}/fittings/{fitting_id}")
    public BaseResponse getFittingGroups(@PathVariable Integer inspection_id,@PathVariable Integer fitting_id) {
        return inspectionService.getFittingGroupById(fitting_id, inspection_id);
    }

    @PostMapping("api/report/maps/inspections/{inspection_id}/fittings")
    public BaseResponse postFittingGroups(@PathVariable Integer inspection_id, @Valid @RequestBody PostFittingsReq postFittingsReq) {
        return inspectionService.postFittingGroups(postFittingsReq,inspection_id);
    }

    @PatchMapping("api/report/maps/inspections/{inspection_id}/fittings/{fitting_id}")
    public BaseResponse patchFittingGroups(@PathVariable Integer inspection_id,@PathVariable Integer fitting_id,
                                           @Valid @RequestBody PostFittingsReq postFittingsReq) {
        return inspectionService.patchFittingGroupById(postFittingsReq,fitting_id, inspection_id);
    }

    @DeleteMapping("api/report/maps/inspections/{inspection_id}/fittings/{fitting_id}")
    public BaseResponse deleteFittingGroups(@PathVariable Integer inspection_id,@PathVariable Integer fitting_id) {
        return inspectionService.deleteFittingGroupById(fitting_id, inspection_id);
    }

    @GetMapping("api/report/maps/inspections/fittings/{fitting_id}/missions")
    public BaseResponse getLocalMissions(@PathVariable Integer fitting_id) {
        return localMissionService.getLocalMissions(fitting_id);
    }

    @GetMapping("api/report/maps/inspections/fittings/{fitting_id}/missions/{mission_id}")
    public BaseResponse getLocalMissionById(@PathVariable Integer fitting_id,@PathVariable Integer mission_id) {
        return localMissionService.getLocalMissionById(mission_id, fitting_id);
    }

    // PostLocalMissionReq 를 사용하지 않고 PostFittingsReq를 사용하는 이유는 컬럼이 name 하나뿐이며, 둘다 not null 이기 때문에
    @PostMapping("api/report/maps/inspections/fittings/{fitting_id}/missions")
    public BaseResponse postLocalMissions(@PathVariable Integer fitting_id, @Valid @RequestBody PostFittingsReq postFittingsReq) {
        return localMissionService.postLocalMissions(postFittingsReq,fitting_id);
    }
    // PostLocalMissionReq 를 사용하지 않고 PostFittingsReq를 사용하는 이유는 컬럼이 name 하나뿐이며, 둘다 not null 이기 때문에
    @PatchMapping("api/report/maps/inspections/fittings/{fitting_id}/missions/{mission_id}")
    public BaseResponse patchLocalMissionsById(@PathVariable Integer fitting_id,@PathVariable Integer mission_id,
                                           @Valid @RequestBody PostFittingsReq postFittingsReq) {
        return localMissionService.patchLocalMissionsById(postFittingsReq,mission_id, fitting_id);
    }

    @DeleteMapping("api/report/maps/inspections/fittings/{fitting_id}/missions/{mission_id}")
    public BaseResponse deleteLocalMission(@PathVariable Integer fitting_id,@PathVariable Integer mission_id) {
        return localMissionService.deleteLocalMission(mission_id, fitting_id);
    }
}
