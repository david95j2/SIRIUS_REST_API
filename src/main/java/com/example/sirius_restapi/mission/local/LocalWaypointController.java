package com.example.sirius_restapi.mission.local;

import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.inspection.analysis.InspectionService;
import com.example.sirius_restapi.mission.local.domain.PatchLocalWaypointReq;
import com.example.sirius_restapi.mission.local.domain.PostFittingsReq;
import com.example.sirius_restapi.mission.local.domain.PostLocalWaypointReq;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class LocalWaypointController {
    private LocalWaypointService localWaypointService;

    @GetMapping("api/report/maps/inspections/fittings/missions/{mission_id}/waypoints")
    public BaseResponse getLocalWaypoints(@PathVariable Integer mission_id) {
        return localWaypointService.getLocalWaypoints(mission_id);
    }

    @GetMapping("api/report/maps/inspections/fittings/missions/{mission_id}/waypoints/{waypoint_id}")
    public BaseResponse getLocalWaypointById(@PathVariable Integer mission_id,@PathVariable Integer waypoint_id) {
        return localWaypointService.getLocalWaypointById(waypoint_id, mission_id);
    }

    @PostMapping("api/report/maps/inspections/fittings/missions/{mission_id}/waypoints")
    public BaseResponse postLocalWaypoints(@PathVariable Integer mission_id, @Valid @RequestBody PostLocalWaypointReq postLocalWaypointReq) {
        return localWaypointService.postLocalWaypoints(postLocalWaypointReq,mission_id);
    }

    @PatchMapping("api/report/maps/inspections/fittings/missions/{mission_id}/waypoints/{waypoint_id}")
    public BaseResponse patchLocalWaypointsById(@PathVariable Integer mission_id,@PathVariable Integer waypoint_id,
                                           @RequestBody PatchLocalWaypointReq patchLocalWaypointReq) {
        return localWaypointService.patchLocalWaypointsById(patchLocalWaypointReq,waypoint_id, mission_id);
    }

    @DeleteMapping("api/report/maps/inspections/fittings/missions/{mission_id}/waypoints/{waypoint_id}")
    public BaseResponse deleteLocalWaypoint(@PathVariable Integer mission_id,@PathVariable Integer waypoint_id) {
        return localWaypointService.deleteLocalWaypoint(waypoint_id, mission_id);
    }
}
