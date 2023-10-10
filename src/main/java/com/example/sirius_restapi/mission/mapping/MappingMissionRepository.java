package com.example.sirius_restapi.mission.mapping;

import com.example.sirius_restapi.mission.mapping.domain.MappingMissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MappingMissionRepository extends JpaRepository<MappingMissionEntity,Integer> {
    @Query("select m from MappingMissionEntity m " +
            "join m.userEntity u where u.loginId=:loginId")
    List<MappingMissionEntity> findAllByIdUserId(@Param("loginId") String loginId);

    @Query("select m from MappingMissionEntity m " +
            "join m.userEntity u where m.id=:missionId and u.loginId=:loginId")
    Optional<MappingMissionEntity> findByIdAndUserId(@Param("missionId") Integer missionId, @Param("loginId") String loginId);

    @Modifying
    @Query(value = "delete gm from global_missions gm " +
            "join users u on u.id=gm.user_id " +
            "where gm.id=:missionId and u.login_id=:loginId",
            nativeQuery = true)
    Integer deleteByIdAndUserId(@Param("missionId") Integer missionId,@Param("loginId") String loginId);
}
