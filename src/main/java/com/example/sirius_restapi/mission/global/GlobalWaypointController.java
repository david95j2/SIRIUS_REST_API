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

    @GetMapping("api/users/{login_id}/missions/{mission_id}/waypoints")
    public BaseResponse getGlobalWaypoints(@PathVariable String login_id, @PathVariable Integer mission_id) {
        userService.getUserByLoginId(login_id);
        return globalWaypointService.getGlobalWaypoints(mission_id);
    }

    @GetMapping("api/users/{login_id}/missions/{mission_id}/waypoints/{waypoint_id}")
    public BaseResponse getGlobalWaypointById(@PathVariable String login_id, @PathVariable Integer mission_id,
                                     @PathVariable Integer waypoint_id) {
        userService.getUserByLoginId(login_id);
        return globalWaypointService.getGlobalWaypointById(waypoint_id, mission_id);
    }

    @PostMapping("api/users/{login_id}/missions/{mission_id}/waypoints")
    public BaseResponse postGlobalWaypoints(@PathVariable String login_id, @PathVariable Integer mission_id,
                                      @Valid @RequestBody PostGlobalWaypointReq postGlobalWaypointReq) {
        userService.getUserByLoginId(login_id);
        return globalWaypointService.postGlobalWaypoints(postGlobalWaypointReq, mission_id);
    }

    @DeleteMapping("api/users/{login_id}/missions/{mission_id}/waypoints/{waypoint_id}")
    @ResponseBody
    public BaseResponse deleteGlobalWayPoints(@PathVariable("login_id") String login_id,@PathVariable("mission_id") Integer mission_id,
                                              @PathVariable("waypoint_id") Integer waypoint_id) {
        userService.getUserByLoginId(login_id);
        return globalWaypointService.deleteGlobalWayPoint(waypoint_id,mission_id);
    }
}
