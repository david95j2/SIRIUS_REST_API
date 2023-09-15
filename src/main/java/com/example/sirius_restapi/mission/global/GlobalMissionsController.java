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

    @GetMapping("api/users/{user_id}/missions")
    public BaseResponse getGlobalMissions(@PathVariable Integer user_id) {
        return globalMissionService.getGlobalMissions(user_id);
    }

    @GetMapping("api/users/{user_id}/missions/{mission_id}")
    public BaseResponse getGlobalMissionById(@PathVariable Integer user_id,@PathVariable Integer mission_id) {
        return globalMissionService.getGlobalMissionById(mission_id,user_id);
    }

    @PostMapping("api/users/{user_id}/missions")
    public BaseResponse postGlobalMissions(@PathVariable Integer user_id, @Valid @RequestBody PostGlobalMissionReq postGlobalMissionReq) {
        return globalMissionService.postGlobalMissions(postGlobalMissionReq,user_id);
    }

    @PatchMapping("api/users/{user_id}/missions/{mission_id}")
    public BaseResponse patchGlobalMissions(@PathVariable Integer user_id,@PathVariable Integer mission_id,
                                      @Valid @RequestBody PatchGlobalMissionReq patchGlobalMissionReq) {
        return globalMissionService.patchGlobalMissions(patchGlobalMissionReq,mission_id,user_id);
    }

    @DeleteMapping("api/users/{user_id}/missions/{mission_id}")
    public BaseResponse deleteGlobalMissions(@PathVariable Integer user_id,@PathVariable Integer mission_id) {
        return globalMissionService.deleteGlobalMissions(mission_id,user_id);
    }
}
