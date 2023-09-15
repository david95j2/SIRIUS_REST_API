package com.example.sirius_restapi.drone;

import com.example.sirius_restapi.drone.domain.PatchDroneReq;
import com.example.sirius_restapi.drone.domain.PostDroneReq;
import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class DroneController {
    private DroneService droneService;
    private UserService userService;
    @GetMapping("api/users/{user_id}/drones")
    public BaseResponse getDrones(@PathVariable Integer user_id) {
        return droneService.getDrones(user_id);
    }

    @GetMapping("api/users/{user_id}/drones/{drone_id}")
    public BaseResponse getDroneById(@PathVariable Integer user_id,@PathVariable Integer drone_id) {
        userService.getUserById(user_id);
        return droneService.getDroneById(drone_id);
    }

    @PostMapping("api/users/{user_id}/drones")
    public BaseResponse postDrone(@PathVariable Integer user_id, @Valid @RequestBody PostDroneReq postDroneReq) {
        return droneService.postDrone(postDroneReq,user_id);
    }

    @PatchMapping("api/users/{user_id}/drones/{drone_id}")
    public BaseResponse patchDroneById(@PathVariable Integer user_id,@PathVariable Integer drone_id,
                                  @Valid @RequestBody PatchDroneReq patchDroneReq) {
        return droneService.patchDroneById(patchDroneReq,drone_id,user_id);
    }

    @DeleteMapping("api/users/{user_id}/drones/{drone_id}")
    public BaseResponse deleteDroneById(@PathVariable Integer user_id,@PathVariable Integer drone_id) {
        return droneService.deleteDroneById(drone_id,user_id);
    }
}
