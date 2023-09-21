package com.example.sirius_restapi.mission.local;

import com.example.sirius_restapi.mission.local.domain.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TypeRepository extends JpaRepository<TypeEntity, Integer> {
    @Query("select t from TypeEntity t join t.localWaypointEntity lw " +
            "where lw.id=:waypointId")
    List<TypeEntity> findAllByWaypointId(@Param("waypointId") Integer waypointId);

    @Query("select t from TypeEntity t join t.localWaypointEntity lw " +
            "where t.id=:typeId and lw.id=:waypointId")
    Optional<TypeEntity> findByIdAndWaypointId(@Param("typeId") Integer typeId,@Param("waypointId") Integer waypointId);

    @Modifying
    @Query("delete from TypeEntity t where t.id=:typeId and t.localWaypointEntity.id=:waypointId")
    Integer deleteByIdAndWaypointId(@Param("typeId") Integer typeId,@Param("waypointId") Integer waypointId);

    @Query("select t from TypeEntity t join t.localWaypointEntity lw " +
            "where lw.id=:waypointId")
    Optional<TypeEntity> findByWayPointId(@Param("waypointId") Integer waypointId);
}
