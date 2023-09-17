package com.example.sirius_restapi.mission.local;

import com.example.sirius_restapi.mission.local.domain.LocalMissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LocalMissionRepository extends JpaRepository<LocalMissionEntity,Integer> {
    @Query("select lm from LocalMissionEntity lm join lm.fittingGroupEntity fg" +
            " where fg.id=:fittingId")
    List<LocalMissionEntity> findAllByFittingId(@Param("fittingId") Integer fittingId);

    @Query("select lm from LocalMissionEntity lm join lm.fittingGroupEntity fg " +
            "where fg.id=:fittingId and lm.id=:missionId")
    Optional<LocalMissionEntity> findByIdAndFittingId(@Param("missionId") Integer missionId,@Param("fittingId") Integer fittingId);

    @Modifying
    @Query("delete from LocalMissionEntity lm " +
            "where lm.id=:missionId and lm.fittingGroupEntity.id=:fittingId")
    Integer deleteByIdAndFittingId(@Param("missionId") Integer missionId,@Param("fittingId") Integer fittingId);
}
