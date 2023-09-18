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
            "join m.userEntity u where u.loginId=:loginId")
    List<GlobalMissionEntity> findAllByIdUserId(@Param("loginId") String loginId);

    @Query("select m from GlobalMissionEntity m " +
            "join m.userEntity u where m.id=:missionId and u.loginId=:loginId")
    Optional<GlobalMissionEntity> findByIdAndUserId(@Param("missionId") Integer missionId, @Param("loginId") String loginId);

    @Modifying
    @Query(value = "delete gm from global_missions gm " +
            "join users u on u.id=gm.user_id " +
            "where gm.id=:missionId and u.login_id=:loginId",
            nativeQuery = true)
    Integer deleteByIdAndUserId(@Param("missionId") Integer missionId,@Param("loginId") String loginId);
}
