package com.example.sirius_restapi.map;

import com.example.sirius_restapi.map.domain.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<LocationEntity, Integer> {
    @Query("select l from LocationEntity l join l.userEntity u " +
            "where l.id=:locationId and u.id=:userId")
    Optional<LocationEntity> findByIdAndUserId(@Param("locationId") Integer locationId,@Param("userId") Integer userId);

    Optional<LocationEntity> findByLocation(String location);
}
