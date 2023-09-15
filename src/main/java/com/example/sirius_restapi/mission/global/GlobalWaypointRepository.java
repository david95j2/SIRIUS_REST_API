package com.example.sirius_restapi.mission.global;

import com.example.sirius_restapi.mission.global.domain.GlobalWayPointEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GlobalWaypointRepository extends JpaRepository<GlobalWayPointEntity,Integer> {

    @Query("select w from GlobalWayPointEntity w join w.missionEntity m where m.id=:missionId order by w.seq")
    List<GlobalWayPointEntity> findAllByMissionId(@Param("missionId") Integer missionId);

    @Query("select w from GlobalWayPointEntity w join w.missionEntity m where m.id=:missionId and w.id=:waypointId")
    Optional<GlobalWayPointEntity> findByIdAndMissionId(@Param("waypointId") Integer waypointId, @Param("missionId") Integer missionId);

    @Transactional
    @Modifying
    @Query("update GlobalWayPointEntity w set w.seq=w.seq+1 " +
            "where w.missionEntity.id=:mission_id and w.seq>=:seq")
    void incrementSeqGreaterThan(@Param("mission_id") Integer missionId, @Param("seq") Integer seq);

    @Transactional
    @Modifying
    @Query("update GlobalWayPointEntity w set w.seq=w.seq-1 " +
            "where w.missionEntity.id=:mission_id and w.seq>=:seq")
    void decrementSeqGreaterThan(@Param("mission_id") Integer missionId, @Param("seq") Integer seq);
}
