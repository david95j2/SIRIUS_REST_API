package com.example.sirius_restapi.mission.plan;

import com.example.sirius_restapi.mission.plan.domain.LocalWaypointEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LocalWaypointRepository extends JpaRepository<LocalWaypointEntity, Integer> {
    @Query("select lw from LocalWaypointEntity lw join lw.localDroneEntity ld " +
            "where ld.id=:droneId order by lw.seq")
    List<LocalWaypointEntity> findByDroneId(@Param("droneId") Integer droneId);

    @Query("select lw from LocalWaypointEntity lw join lw.localDroneEntity ld " +
            "where lw.id=:waypointId and ld.id=:droneId")
    Optional<LocalWaypointEntity> findByIdAndDroneId(@Param("waypointId") Integer waypointId, @Param("droneId") Integer droneId);

//    @Modifying
//    @Query("delete from LocalWaypointEntity lw " +
//            "where lw.id=:waypointId and lw.localMissionEntity.id=:missionId")
//    Integer deleteByIdAndMissionId(@Param("waypointId") Integer waypointId,@Param("missionId") Integer missionId);

    @Transactional
    @Modifying
    @Query("update LocalWaypointEntity lw set lw.seq=lw.seq+1 " +
            "where lw.localDroneEntity.id=:droneId and lw.seq>=:seq")
    void incrementSeqGreaterThan(@Param("droneId") Integer droneId, @Param("seq") Integer seq);

    @Transactional
    @Modifying
    @Query("update LocalWaypointEntity lw set lw.seq=lw.seq-1 " +
            "where lw.localDroneEntity.id=:droneId and lw.seq>=:seq")
    void decrementSeqGreaterThan(@Param("droneId") Integer droneId, @Param("seq") Integer seq);
}
