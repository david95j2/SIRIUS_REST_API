package com.example.sirius_restapi.mission;

import com.example.sirius_restapi.mission.domain.PatchMissionReq;
import com.example.sirius_restapi.mission.domain.PostMissionReq;
import com.example.sirius_restapi.exception.BaseResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class MissionsController {
    private MissionService missionService;

    @GetMapping("api/users/{user_id}/missions")
    public BaseResponse getMissions(@PathVariable Integer user_id) {
        return missionService.getMissions(user_id);
    }

    @GetMapping("api/users/{user_id}/missions/{mission_id}")
    public BaseResponse getMissionById(@PathVariable Integer user_id,@PathVariable Integer mission_id) {
        return missionService.getMissionById(mission_id,user_id);
    }

    @PostMapping("api/users/{user_id}/missions")
    public BaseResponse postMissions(@PathVariable Integer user_id, @Valid @RequestBody PostMissionReq postMissionReq) {
        return missionService.postMissions(postMissionReq,user_id);
    }

    @PatchMapping("api/users/{user_id}/missions/{mission_id}")
    public BaseResponse patchMissions(@PathVariable Integer user_id,@PathVariable Integer mission_id,
                                      @Valid @RequestBody PatchMissionReq patchMissionReq) {
        return missionService.patchMissions(patchMissionReq,mission_id,user_id);
    }

    @DeleteMapping("api/users/{user_id}/missions/{mission_id}")
    public BaseResponse deleteMissions(@PathVariable Integer user_id,@PathVariable Integer mission_id) {
        return missionService.deleteMissions(mission_id,user_id);
    }
}
