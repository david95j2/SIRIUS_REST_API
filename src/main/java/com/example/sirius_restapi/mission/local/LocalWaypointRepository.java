package com.example.sirius_restapi.mission.local;

import com.example.sirius_restapi.mission.local.domain.LocalWaypointEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LocalWaypointRepository extends JpaRepository<LocalWaypointEntity, Integer> {
    @Query("select lw from LocalWaypointEntity lw join lw.localMissionEntity lm " +
            "where lm.id=:missionId order by lw.seq")
    List<LocalWaypointEntity> findByMissionId(@Param("missionId") Integer missionId);

    @Query("select lw from LocalWaypointEntity lw join lw.localMissionEntity lm " +
            "where lw.id=:waypointId and lm.id=:missionId")
    Optional<LocalWaypointEntity> findByIdAndMissionId(@Param("waypointId") Integer waypointId, @Param("missionId") Integer missionId);

//    @Modifying
//    @Query("delete from LocalWaypointEntity lw " +
//            "where lw.id=:waypointId and lw.localMissionEntity.id=:missionId")
//    Integer deleteByIdAndMissionId(@Param("waypointId") Integer waypointId,@Param("missionId") Integer missionId);

    @Transactional
    @Modifying
    @Query("update LocalWaypointEntity lw set lw.seq=lw.seq+1 " +
            "where lw.localMissionEntity.id=:mission_id and lw.seq>=:seq")
    void incrementSeqGreaterThan(@Param("mission_id") Integer missionId, @Param("seq") Integer seq);

    @Transactional
    @Modifying
    @Query("update LocalWaypointEntity lw set lw.seq=lw.seq-1 " +
            "where lw.localMissionEntity.id=:mission_id and lw.seq>=:seq")
    void decrementSeqGreaterThan(@Param("mission_id") Integer missionId, @Param("seq") Integer seq);
}
