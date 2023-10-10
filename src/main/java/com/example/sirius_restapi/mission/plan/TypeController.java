package com.example.sirius_restapi.mission.plan;

import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.mission.plan.domain.PatchTypeReq;
import com.example.sirius_restapi.mission.plan.domain.PostTypeReq;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TypeController {
    private TypeService typeService;

    @GetMapping("api/report/maps/inspections/missions/drones/waypoints/{waypoint_id}/types")
    public BaseResponse getTypes(@PathVariable Integer waypoint_id) {
        return typeService.getTypes(waypoint_id);
    }

    @GetMapping("api/report/maps/inspections/missions/drones/waypoints/{waypoint_id}/types/{type_id}")
    public BaseResponse getTypeById(@PathVariable Integer waypoint_id,@PathVariable Integer type_id) {
        return typeService.getTypeById(type_id, waypoint_id);
    }

    @PostMapping("api/report/maps/inspections/missions/drones/waypoints/{waypoint_id}/types")
    public BaseResponse postTypes(@PathVariable Integer waypoint_id, @Valid @RequestBody PostTypeReq postTypeReq) {
        return typeService.postTypes(postTypeReq,waypoint_id);
    }

    @PatchMapping("api/report/maps/inspections/missions/drones/waypoints/{waypoint_id}/types/{type_id}")
    public BaseResponse patchTypesById(@PathVariable Integer waypoint_id,@PathVariable Integer type_id,
                                           @Valid @RequestBody PatchTypeReq patchTypeReq) {
        return typeService.patchTypesById(patchTypeReq,type_id, waypoint_id);
    }

    @DeleteMapping("api/report/maps/inspections/missions/drones/waypoints/{waypoint_id}/types/{type_id}")
    public BaseResponse deleteTypes(@PathVariable Integer waypoint_id,@PathVariable Integer type_id) {
        return typeService.deleteTypes(type_id, waypoint_id);
    }
}
