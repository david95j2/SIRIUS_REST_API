package com.example.sirius_restapi.drone;

import com.example.sirius_restapi.drone.domain.DroneEntity;
import com.example.sirius_restapi.drone.domain.PatchDroneReq;
import com.example.sirius_restapi.drone.domain.PatchDroneRes;
import com.example.sirius_restapi.drone.domain.PostDroneReq;
import com.example.sirius_restapi.exception.AppException;
import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.exception.ErrorCode;
import com.example.sirius_restapi.user.UserService;
import com.example.sirius_restapi.user.domain.UserEntity;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DroneService {
    private DroneRepository droneRepository;
    private UserService userService;
    public BaseResponse getDrones(Integer userId) {
        return new BaseResponse(ErrorCode.SUCCESS, droneRepository.findAllByUserId(userId));
    }

    public BaseResponse getDroneById(Integer droneId) {
        return new BaseResponse(ErrorCode.SUCCESS, droneRepository.findById(droneId));
    }

    public BaseResponse postDrone(@Valid PostDroneReq postDroneReq, Integer userId) {
        UserEntity userEntity = (UserEntity) userService.getUserById(userId).getResult();
        DroneEntity droneEntity = DroneEntity.builder()
                .userEntity(userEntity)
                .droneVoltageMin(postDroneReq.getMin())
                .droneVoltageMax(postDroneReq.getMax())
                .droneType(postDroneReq.getName())
                .xDimension(postDroneReq.getX_dimension())
                .yDimension(postDroneReq.getY_dimension())
                .zDimension(postDroneReq.getZ_dimension())
                .build();
        Integer drone_id = droneRepository.save(droneEntity).getId();
        return new BaseResponse(ErrorCode.CREATED,Integer.valueOf(drone_id)+"번 드론이 생성되었습니다.");
    }

    public BaseResponse patchDroneById(PatchDroneReq patchDroneReq, Integer droneId, Integer userId) {
        UserEntity userEntity = (UserEntity) userService.getUserById(userId).getResult();
        DroneEntity droneEntity = droneRepository.findById(droneId).orElseThrow(()->new AppException(ErrorCode.DATA_NOT_FOUND));
        // 값이 있는 것만 수정
        if (patchDroneReq.getMin() != null) {
            droneEntity.setDroneVoltageMin(patchDroneReq.getMin());
        }

        if (patchDroneReq.getMax() != null) {
            droneEntity.setDroneVoltageMax(patchDroneReq.getMax());
        }

        if (patchDroneReq.getName() != null) {
            droneEntity.setDroneType(patchDroneReq.getName());
        }

        if (patchDroneReq.getX_dimension() != null) {
            droneEntity.setXDimension(patchDroneReq.getX_dimension());
        }

        if (patchDroneReq.getY_dimension() != null) {
            droneEntity.setYDimension(patchDroneReq.getY_dimension());
        }

        if (patchDroneReq.getZ_dimension() != null) {
            droneEntity.setZDimension(patchDroneReq.getZ_dimension());
        }

        // Save the updated entity
        DroneEntity updated = droneRepository.save(droneEntity);
        PatchDroneRes patchDroneRes = updated.toDto();

        return new BaseResponse(ErrorCode.SUCCESS, patchDroneRes);
    }

    @Transactional
    public BaseResponse deleteDroneById(Integer droneId, Integer userId) {
        Integer deletedCount = droneRepository.deleteByIdAndUserId(droneId,userId);
        if (deletedCount != 0) {
            return new BaseResponse(ErrorCode.SUCCESS,Integer.valueOf(droneId)+"번 드론이 삭제되었습니다.");
        } else {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);:
        }
    }
}
