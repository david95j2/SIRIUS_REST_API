package com.example.sirius_restapi.mission.global;

import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.mission.global.domain.PostGlobalWaypointReq;
import com.example.sirius_restapi.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class GlobalWaypointController {
    private GlobalWaypointService globalWaypointService;
    private UserService userService;

    @GetMapping("api/users/{user_id}/missions/{mission_id}/waypoints")
    public BaseResponse getGlobalWaypoints(@PathVariable Integer user_id, @PathVariable Integer mission_id) {
        userService.getUserById(user_id);
        return globalWaypointService.getGlobalWaypoints(mission_id);
    }

    @GetMapping("api/users/{user_id}/missions/{mission_id}/waypoints/{waypoint_id}")
    public BaseResponse getGlobalWaypointById(@PathVariable Integer user_id, @PathVariable Integer mission_id,
                                     @PathVariable Integer waypoint_id) {
        userService.getUserById(user_id);
        return globalWaypointService.getGlobalWaypointById(waypoint_id, mission_id);
    }

    @PostMapping("api/users/{user_id}/missions/{mission_id}/waypoints")
    public BaseResponse postGlobalWaypoints(@PathVariable Integer user_id, @PathVariable Integer mission_id,
                                      @Valid @RequestBody PostGlobalWaypointReq postGlobalWaypointReq) {
        userService.getUserById(user_id);
        return globalWaypointService.postGlobalWaypoints(postGlobalWaypointReq, mission_id);
    }

    @DeleteMapping("api/users/{user_id}/missions/{mission_id}/waypoints/{waypoint_id}")
    @ResponseBody
    public BaseResponse deleteGlobalWayPoints(@PathVariable("user_id") Integer user_id,@PathVariable("waypoint_id") Integer waypoint_id) {
        userService.getUserById(user_id);
        return globalWaypointService.deleteGlobalWayPoint(waypoint_id);
    }
}
