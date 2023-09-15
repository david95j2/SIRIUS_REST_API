package com.example.sirius_restapi.drone;

import com.example.sirius_restapi.drone.domain.DroneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DroneRepository extends JpaRepository<DroneEntity, Integer> {
    @Query("select d from DroneEntity d " +
            "join d.userEntity u where u.id=:userId")
    List<DroneEntity> findAllByUserId(@Param("userId") Integer userId);
    @Modifying
    @Query("delete from DroneEntity d " +
            "where d.id=:droneId and d.userEntity.id=:userId")
    Integer deleteByIdAndUserId(@Param("droneId") Integer droneId,@Param("userId") Integer userId);
}
