package com.example.sirius_restapi.inspection.analysis;

import com.example.sirius_restapi.inspection.analysis.domain.InspectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InspectionRepository extends JpaRepository<InspectionEntity, Integer> {
    @Query("SELECT i FROM InspectionEntity i " +
            "WHERE i.mapGroupEntity.id = (SELECT m.mapGroupEntity.id FROM MapEntity m WHERE m.id=:mapId)")
    List<InspectionEntity> findByMapId(@Param("mapId")Integer mapId);
}
