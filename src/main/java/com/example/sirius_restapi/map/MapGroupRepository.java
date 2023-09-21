package com.example.sirius_restapi.map;

import com.example.sirius_restapi.map.domain.MapGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MapGroupRepository extends JpaRepository<MapGroupEntity, Integer> {
    @Query("select mg from MapGroupEntity mg join mg.locationEntity l " +
            "where l.id=:locationId and mg.id=:mapGroupId")
    Optional<MapGroupEntity> findByIdAndLocationId(@Param("mapGroupId") Integer mapGroupId, @Param("locationId") Integer locationId);
}
