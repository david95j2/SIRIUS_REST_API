package com.example.sirius_restapi.mission.local;

import com.example.sirius_restapi.mission.local.domain.LocalMissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LocalMissionRepository extends JpaRepository<LocalMissionEntity, Integer> {
    @Query("select lm from LocalMissionEntity lm join lm.inspectionEntity i " +
            "where i.id=:inspectionId and lm.id=:missionId")
    Optional<LocalMissionEntity> findByIdAndInspectId(@Param("missionId") Integer missionId, @Param("inspectionId") Integer inspectionId);

    @Modifying
    @Query("delete from LocalMissionEntity lm " +
            "where lm.id=:missionId and lm.inspectionEntity.id=:inspectionId")
    Integer deleteByIdAndInspectId(@Param("missionId") Integer missionId, @Param("inspectionId") Integer inspectionId);
}
