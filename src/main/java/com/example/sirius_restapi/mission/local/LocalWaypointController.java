package com.example.sirius_restapi.mission.local;

import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.mission.local.domain.PatchLocalWaypointReq;
import com.example.sirius_restapi.mission.local.domain.PostLocalWaypointReq;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class LocalWaypointController {
    private LocalWaypointService localWaypointService;

    @GetMapping("api/report/maps/inspections/missions/drones/{drone_id}/waypoints")
    public BaseResponse getLocalWaypoints(@PathVariable Integer drone_id) {
        return localWaypointService.getLocalWaypoints(drone_id);
    }

    @GetMapping("api/report/maps/inspections/missions/drones/{drone_id}/waypoints/{waypoint_id}")
    public BaseResponse getLocalWaypointById(@PathVariable Integer drone_id,@PathVariable Integer waypoint_id) {
        return localWaypointService.getLocalWaypointById(waypoint_id, drone_id);
    }

    @PostMapping("api/report/maps/inspections/missions/drones/{drone_id}/waypoints")
    public BaseResponse postLocalWaypoints(@PathVariable Integer drone_id, @Valid @RequestBody PostLocalWaypointReq postLocalWaypointReq) {
        return localWaypointService.postLocalWaypoints(postLocalWaypointReq,drone_id);
    }

    @PatchMapping("api/report/maps/inspections/missions/drones/{drone_id}/waypoints/{waypoint_id}")
    public BaseResponse patchLocalWaypointsById(@PathVariable Integer drone_id,@PathVariable Integer waypoint_id,
                                           @RequestBody PatchLocalWaypointReq patchLocalWaypointReq) {
        return localWaypointService.patchLocalWaypointsById(patchLocalWaypointReq,waypoint_id, drone_id);
    }

    @DeleteMapping("api/report/maps/inspections/missions/drones/{drone_id}/waypoints/{waypoint_id}")
    public BaseResponse deleteLocalWaypoint(@PathVariable Integer drone_id,@PathVariable Integer waypoint_id) {
        return localWaypointService.deleteLocalWaypoint(waypoint_id, drone_id);
    }
}
