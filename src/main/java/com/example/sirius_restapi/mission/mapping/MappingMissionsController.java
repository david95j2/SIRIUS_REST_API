package com.example.sirius_restapi.mission.mapping;

import com.example.sirius_restapi.mission.mapping.domain.PatchMappingMissionReq;
import com.example.sirius_restapi.mission.mapping.domain.PostMappingMissionReq;
import com.example.sirius_restapi.exception.BaseResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class MappingMissionsController {
    private MappingMissionService mappingMissionService;

    @GetMapping("api/users/{login_id}/missions")
    public BaseResponse getGlobalMissions(@PathVariable String login_id) {
        return mappingMissionService.getGlobalMissions(login_id);
    }

    @GetMapping("api/users/{login_id}/missions/{mission_id}")
    public BaseResponse getGlobalMissionById(@PathVariable String login_id,@PathVariable Integer mission_id) {
        return mappingMissionService.getGlobalMissionById(mission_id,login_id);
    }

    @PostMapping("api/users/{login_id}/missions")
    public BaseResponse postGlobalMissions(@PathVariable String login_id, @Valid @RequestBody PostMappingMissionReq postMappingMissionReq) {
        return mappingMissionService.postGlobalMissions(postMappingMissionReq,login_id);
    }

    @PatchMapping("api/users/{login_id}/missions/{mission_id}")
    public BaseResponse patchGlobalMissions(@PathVariable String login_id,@PathVariable Integer mission_id,
                                      @Valid @RequestBody PatchMappingMissionReq patchMappingMissionReq) {
        return mappingMissionService.patchGlobalMissions(patchMappingMissionReq,mission_id,login_id);
    }

    @DeleteMapping("api/users/{login_id}/missions/{mission_id}")
    public BaseResponse deleteGlobalMissions(@PathVariable String login_id,@PathVariable Integer mission_id) {
        return mappingMissionService.deleteGlobalMissions(mission_id,login_id);
    }
}
