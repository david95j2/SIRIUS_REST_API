package com.example.sirius_restapi.inspection.analysis;

import com.example.sirius_restapi.inspection.analysis.domain.InspectionEntity;
import com.example.sirius_restapi.mission.local.domain.LocalMissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InspectionRepository extends JpaRepository<InspectionEntity, Integer> {
    @Query("SELECT i FROM InspectionEntity i " +
            "WHERE i.mapGroupEntity.id = (SELECT m.mapGroupEntity.id FROM MapEntity m WHERE m.id=:mapId)")
    List<InspectionEntity> findByMapId(@Param("mapId")Integer mapId);

    @Query("select lm from InspectionEntity i join i.localMissionEntities lm " +
            "where i.id=:inspectionId")
    List<LocalMissionEntity> findLocalMissionAllByInspectId(@Param("inspectionId") Integer inspectionId);

    @Query("select lm from InspectionEntity i join i.localMissionEntities lm " +
            "where i.id=:inspectionId and lm.id=:missionId")
    Optional<LocalMissionEntity> findByIdAndMissionId(@Param("inspectionId") Integer inspectionId, @Param("missionId") Integer missionId);
}
