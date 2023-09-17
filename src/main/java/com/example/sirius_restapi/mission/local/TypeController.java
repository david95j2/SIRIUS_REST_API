package com.example.sirius_restapi.mission.local;

import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.mission.local.domain.PatchLocalWaypointReq;
import com.example.sirius_restapi.mission.local.domain.PatchTypeReq;
import com.example.sirius_restapi.mission.local.domain.PostLocalWaypointReq;
import com.example.sirius_restapi.mission.local.domain.PostTypeReq;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TypeController {
    private TypeService typeService;

    @GetMapping("api/report/maps/inspections/fittings/missions/{mission_id}/types")
    public BaseResponse getTypes(@PathVariable Integer mission_id) {
        return typeService.getTypes(mission_id);
    }

    @GetMapping("api/report/maps/inspections/fittings/missions/{mission_id}/types/{type_id}")
    public BaseResponse getTypeById(@PathVariable Integer mission_id,@PathVariable Integer type_id) {
        return typeService.getTypeById(type_id, mission_id);
    }

    @PostMapping("api/report/maps/inspections/fittings/missions/{mission_id}/types")
    public BaseResponse postTypes(@PathVariable Integer mission_id, @Valid @RequestBody PostTypeReq postTypeReq) {
        return typeService.postTypes(postTypeReq,mission_id);
    }

    @PatchMapping("api/report/maps/inspections/fittings/missions/{mission_id}/types/{type_id}")
    public BaseResponse patchTypesById(@PathVariable Integer mission_id,@PathVariable Integer type_id,
                                           @Valid @RequestBody PatchTypeReq patchTypeReq) {
        return typeService.patchTypesById(patchTypeReq,type_id, mission_id);
    }

    @DeleteMapping("api/report/maps/inspections/fittings/missions/{mission_id}/types/{type_id}")
    public BaseResponse deleteTypes(@PathVariable Integer mission_id,@PathVariable Integer type_id) {
        return typeService.deleteTypes(type_id, mission_id);
    }
}
