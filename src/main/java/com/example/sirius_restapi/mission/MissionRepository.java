package com.example.sirius_restapi.mission;

import com.example.sirius_restapi.mission.domain.MissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MissionRepository extends JpaRepository<MissionEntity,Integer> {
    @Query("select m from MissionEntity m " +
            "join m.userEntity u where u.id=:userId")
    List<MissionEntity> findAllByIdUserId(@Param("userId") Integer userId);

    @Query("select m from MissionEntity m " +
            "join m.userEntity u where m.id=:missionId and u.id=:userId")
    Optional<MissionEntity> findByIdAndUserId(@Param("missionId") Integer missionId, @Param("userId") Integer userId);

    @Modifying
    @Query("delete from MissionEntity m " +
            "where m.id=:missionId and m.userEntity.id=:userId")
    Integer deleteByIdAndUserId(@Param("missionId") Integer missionId,@Param("userId") Integer userId);
}
