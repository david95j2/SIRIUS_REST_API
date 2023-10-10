package com.example.sirius_restapi.mission.plan;

import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.inspection.analysis.InspectionService;
import com.example.sirius_restapi.mission.plan.domain.PostLocalMissionReq;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class LocalDroneController {
    private LocalDroneService localDroneService;
    private InspectionService inspectionService;

    @GetMapping("api/report/maps/inspections/{inspection_id}/missions")
    public BaseResponse getLocalMissions(@PathVariable Integer inspection_id) {
        return inspectionService.getLocalMissions(inspection_id);
    }

    @GetMapping("api/report/maps/inspections/{inspection_id}/missions/{mission_id}")
    public BaseResponse getLocalMissionById(@PathVariable Integer inspection_id,@PathVariable Integer mission_id) {
        return inspectionService.getLocalMissionById(mission_id, inspection_id);
    }

    @PostMapping("api/report/maps/inspections/{inspection_id}/missions")
    public BaseResponse postLocalMissions(@PathVariable Integer inspection_id, @Valid @RequestBody PostLocalMissionReq postLocalMissionReq) {
        return inspectionService.postLocalMissions(postLocalMissionReq,inspection_id);
    }

    @PatchMapping("api/report/maps/inspections/{inspection_id}/missions/{mission_id}")
    public BaseResponse patchLocalMissions(@PathVariable Integer inspection_id,@PathVariable Integer mission_id,
                                           @Valid @RequestBody PostLocalMissionReq postLocalMissionReq) {
        return inspectionService.patchLocalMissions(postLocalMissionReq,mission_id, inspection_id);
    }

    @DeleteMapping("api/report/maps/inspections/{inspection_id}/missions/{mission_id}")
    public BaseResponse deleteLocalMissions(@PathVariable Integer inspection_id,@PathVariable Integer mission_id) {
        return inspectionService.deleteLocalMissions(mission_id, inspection_id);
    }

    @GetMapping("api/report/maps/inspections/missions/{mission_id}/drones")
    public BaseResponse getLocalDrones(@PathVariable Integer mission_id) {
        return localDroneService.getLocalDrones(mission_id);
    }

    @GetMapping("api/report/maps/inspections/missions/{mission_id}/drones/{drone_id}")
    public BaseResponse getLocalDroneById(@PathVariable Integer mission_id,@PathVariable Integer drone_id) {
        return localDroneService.getLocalDroneById(drone_id, mission_id);
    }

    // PostLocalMissionReq 를 사용하지 않고 PostFittingsReq를 사용하는 이유는 컬럼이 name 하나뿐이며, 둘다 not null 이기 때문에
    @PostMapping("api/report/maps/inspections/missions/{mission_id}/drones")
    public BaseResponse postLocalDrones(@PathVariable Integer mission_id, @Valid @RequestBody PostLocalMissionReq postLocalMissionReq) {
        return localDroneService.postLocalDrones(postLocalMissionReq,mission_id);
    }
    // PostLocalMissionReq 를 사용하지 않고 PostFittingsReq를 사용하는 이유는 컬럼이 name 하나뿐이며, 둘다 not null 이기 때문에
    @PatchMapping("api/report/maps/inspections/missions/{mission_id}/drones/{drone_id}")
    public BaseResponse patchLocalDrones(@PathVariable Integer mission_id,@PathVariable Integer drone_id,
                                           @Valid @RequestBody PostLocalMissionReq postLocalMissionReq) {
        return localDroneService.patchLocalDrones(postLocalMissionReq,drone_id, mission_id);
    }

    @DeleteMapping("api/report/maps/inspections/missions/{mission_id}/drones/{drone_id}")
    public BaseResponse deleteLocalDrones(@PathVariable Integer mission_id,@PathVariable Integer drone_id) {
        return localDroneService.deleteLocalDrones(drone_id, mission_id);
    }
}
