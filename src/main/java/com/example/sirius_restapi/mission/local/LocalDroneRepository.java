package com.example.sirius_restapi.mission.local;

import com.example.sirius_restapi.mission.local.domain.LocalDroneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LocalDroneRepository extends JpaRepository<LocalDroneEntity,Integer> {
    @Query("select ld from LocalDroneEntity ld join ld.localMissionEntity lm " +
            " where lm.id=:missionId")
    List<LocalDroneEntity> findAllByMissionId(@Param("missionId") Integer missionId);

    @Query("select ld from LocalDroneEntity ld join ld.localMissionEntity lm " +
            "where lm.id=:missionId and ld.id=:droneId")
    Optional<LocalDroneEntity> findByIdAndMissionId(@Param("droneId") Integer droneId, @Param("missionId") Integer missionId);

    @Modifying
    @Query("delete from LocalDroneEntity ld " +
            "where ld.id=:droneId and ld.localMissionEntity.id=:missionId")
    Integer deleteByIdAndMissionId(@Param("droneId") Integer droneId,@Param("missionId") Integer missionId);
}
