package com.example.sirius_restapi.mission.local;

import com.example.sirius_restapi.mission.local.domain.FittingGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FittingGroupRepository extends JpaRepository<FittingGroupEntity, Integer> {
    @Query("select fg from FittingGroupEntity fg join fg.inspectionEntity i " +
            "where i.id=:inspectionId and fg.id=:fittingId")
    Optional<FittingGroupEntity> findByIdAndInspectId(@Param("fittingId") Integer fittingId, @Param("inspectionId") Integer inspectionId);

    @Modifying
    @Query("delete from FittingGroupEntity fg " +
            "where fg.id=:fittingId and fg.inspectionEntity.id=:inspectionId")
    Integer deleteByIdAndInspectId(@Param("fittingId") Integer fittingId, @Param("inspectionId") Integer inspectionId);
}
