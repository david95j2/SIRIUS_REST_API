package com.example.sirius_restapi.inspection.analysis;

import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.inspection.analysis.domain.PostInspectionReq;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class InspectionController {
    private InspectionService inspectionService;

    @GetMapping("api/report/maps/{map_id}/inspections")
    public BaseResponse getInspections(@PathVariable Integer map_id) {
        return inspectionService.getInspections(map_id);
    }

    @GetMapping("api/report/maps/{map_id}/inspections/{inspection_id}")
    public BaseResponse getInspectionById(@PathVariable Integer map_id,@PathVariable Integer inspection_id) {
        return inspectionService.getInspectionById(inspection_id);
    }

    // Fitting Program start!
    @PostMapping("api/report/maps/{map_id}/inspections")
    public BaseResponse postInspectionAndStartFittingApp(@PathVariable Integer map_id, @Valid @RequestBody PostInspectionReq inspectionReq) {
        return inspectionService.postInspectAndStartFittingApp(inspectionReq,map_id);
    }
}
