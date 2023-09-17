package com.example.sirius_restapi.inspection.analysis;

import com.example.sirius_restapi.inspection.analysis.domain.InspectionEntity;
import com.example.sirius_restapi.mission.local.domain.FittingGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InspectionRepository extends JpaRepository<InspectionEntity, Integer> {
    @Query("SELECT i FROM InspectionEntity i " +
            "WHERE i.mapGroupEntity.id = (SELECT m.mapGroupEntity.id FROM MapEntity m WHERE m.id=:mapId)")
    List<InspectionEntity> findByMapId(@Param("mapId")Integer mapId);

    @Query("select fg from InspectionEntity i join i.fittingGroupEntities fg " +
            "where i.id=:inspectionId")
    List<FittingGroupEntity> findFittingsAllByInspectId(@Param("inspectionId") Integer inspectionId);

    @Query("select fg from InspectionEntity i join i.fittingGroupEntities fg " +
            "where i.id=:inspectionId and fg.id=:fittingId")
    Optional<FittingGroupEntity> findByIdAndFittingId(@Param("inspectionId") Integer inspectionId,@Param("fittingId") Integer fittingId);
}
