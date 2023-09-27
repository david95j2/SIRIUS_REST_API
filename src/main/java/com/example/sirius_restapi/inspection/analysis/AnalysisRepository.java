package com.example.sirius_restapi.inspection.analysis;

import com.example.sirius_restapi.inspection.analysis.domain.AnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnalysisRepository extends JpaRepository<AnalysisEntity, Integer> {
    @Query("select a from AnalysisEntity a join a.inspectionEntity i " +
            "where i.id=:inspectionId")
    List<AnalysisEntity> findByInspectId(@Param("inspectionId") Integer inspectionId);
    @Query("select a from AnalysisEntity a join a.inspectionEntity i " +
            "where a.id=:analysisId and i.id=:inspectionId")
    Optional<AnalysisEntity> findByIdAndInspectId(@Param("analysisId") Integer analysisId,@Param("inspectionId") Integer inspectionId);
    @Query("select max(a.id) from AnalysisEntity a ")
    Integer findMaxId(@Param("inspectionId") Integer inspectionId);
}
