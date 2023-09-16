package com.example.sirius_restapi.inspection.analysis;

import com.example.sirius_restapi.exception.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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


}
