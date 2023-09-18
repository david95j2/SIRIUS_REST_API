package com.example.sirius_restapi.mission.global;

import com.example.sirius_restapi.mission.global.domain.PatchGlobalMissionReq;
import com.example.sirius_restapi.mission.global.domain.PostGlobalMissionReq;
import com.example.sirius_restapi.exception.BaseResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class GlobalMissionsController {
    private GlobalMissionService globalMissionService;

    @GetMapping("api/users/{login_id}/missions")
    public BaseResponse getGlobalMissions(@PathVariable String login_id) {
        return globalMissionService.getGlobalMissions(login_id);
    }

    @GetMapping("api/users/{login_id}/missions/{mission_id}")
    public BaseResponse getGlobalMissionById(@PathVariable String login_id,@PathVariable Integer mission_id) {
        return globalMissionService.getGlobalMissionById(mission_id,login_id);
    }

    @PostMapping("api/users/{login_id}/missions")
    public BaseResponse postGlobalMissions(@PathVariable String login_id, @Valid @RequestBody PostGlobalMissionReq postGlobalMissionReq) {
        return globalMissionService.postGlobalMissions(postGlobalMissionReq,login_id);
    }

    @PatchMapping("api/users/{login_id}/missions/{mission_id}")
    public BaseResponse patchGlobalMissions(@PathVariable String login_id,@PathVariable Integer mission_id,
                                      @Valid @RequestBody PatchGlobalMissionReq patchGlobalMissionReq) {
        return globalMissionService.patchGlobalMissions(patchGlobalMissionReq,mission_id,login_id);
    }

    @DeleteMapping("api/users/{login_id}/missions/{mission_id}")
    public BaseResponse deleteGlobalMissions(@PathVariable String login_id,@PathVariable Integer mission_id) {
        return globalMissionService.deleteGlobalMissions(mission_id,login_id);
    }
}
