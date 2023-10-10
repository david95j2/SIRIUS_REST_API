package com.example.sirius_restapi.mission.plan;

import com.example.sirius_restapi.mission.plan.domain.PlansEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LocalMissionRepository extends JpaRepository<PlansEntity, Integer> {
    @Query("select lm from PlansEntity lm join lm.inspectionEntity i " +
            "where i.id=:inspectionId and lm.id=:missionId")
    Optional<PlansEntity> findByIdAndInspectId(@Param("missionId") Integer missionId, @Param("inspectionId") Integer inspectionId);

    @Modifying
    @Query("delete from PlansEntity lm " +
            "where lm.id=:missionId and lm.inspectionEntity.id=:inspectionId")
    Integer deleteByIdAndInspectId(@Param("missionId") Integer missionId, @Param("inspectionId") Integer inspectionId);
}
