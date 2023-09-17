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

    // (1) api/report/maps/{map_id}/inspections/{inspection_id}/fitting로 묶인다.
    // (2) api/report/maps/{map_id}/inspections/fitting
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
}
