package com.example.sirius_restapi.map;

import com.example.sirius_restapi.map.domain.GetMapsMapping;
import com.example.sirius_restapi.map.domain.LocationEntity;
import com.example.sirius_restapi.map.domain.MapEntity;
import com.example.sirius_restapi.map.domain.ThumbnailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MapRepository extends JpaRepository<MapEntity,Integer> {
    @Query("select l from LocationEntity l " +
            "join l.userEntity u where u.id=:userId")
    List<LocationEntity> findAllByUserId(@Param("userId") Integer userId);

    @Query("select l from LocationEntity l join l.userEntity u " +
            "where u.id=:userId and l.id=:locationId")
    Optional<LocationEntity> findByLocationIdAndUserId(@Param("locationId") Integer locationId,@Param("userId") Integer userId);


    // thumbnail 가져오기 용 쿼리
    @Query("select t from LocationEntity l join l.thumbnailEntities t " +
            "where l.id=:locationId order by t.thumbnailRegdate desc limit 1")
    Optional<ThumbnailEntity> findByLocationId(@Param("locationId") Integer locationId);

}
