package com.example.sirius_restapi.mission.local;

import com.example.sirius_restapi.mission.local.domain.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TypeRepository extends JpaRepository<TypeEntity, Integer> {
    @Query("select t from TypeEntity t join t.localMissionEntity lm " +
            "where lm.id=:missionId")
    List<TypeEntity> findAllByMissionId(@Param("missionId") Integer missionId);

    @Query("select t from TypeEntity t join t.localMissionEntity lm " +
            "where t.id=:typeId and lm.id=:missionId")
    Optional<TypeEntity> findByIdAndMissionId(Integer typeId, Integer missionId);

    @Modifying
    @Query("delete from TypeEntity t where t.id=:typeId and t.localMissionEntity.id=:missionId")
    Integer deleteByIdAndMissionId(@Param("typeId") Integer typeId,@Param("missionId") Integer missionId);
}
