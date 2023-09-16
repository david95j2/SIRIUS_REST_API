package com.example.sirius_restapi.mission.global;

import com.example.sirius_restapi.mission.global.domain.GlobalMissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GlobalMissionRepository extends JpaRepository<GlobalMissionEntity,Integer> {
    @Query("select m from GlobalMissionEntity m " +
            "join m.userEntity u where u.id=:userId")
    List<GlobalMissionEntity> findAllByIdUserId(@Param("userId") Integer userId);

    @Query("select m from GlobalMissionEntity m " +
            "join m.userEntity u where m.id=:missionId and u.id=:userId")
    Optional<GlobalMissionEntity> findByIdAndUserId(@Param("missionId") Integer missionId, @Param("userId") Integer userId);

    @Modifying
    @Query("delete from GlobalMissionEntity m " +
            "where m.id=:missionId and m.userEntity.id=:userId")
    Integer deleteByIdAndUserId(@Param("missionId") Integer missionId,@Param("userId") Integer userId);
}
