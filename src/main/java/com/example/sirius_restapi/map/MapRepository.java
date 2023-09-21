package com.example.sirius_restapi.map;

import com.example.sirius_restapi.map.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface MapRepository extends JpaRepository<MapEntity,Integer> {
    @Query("select l from LocationEntity l " +
            "join l.userEntity u where u.loginId=:loginId")
    List<LocationEntity> findAllByLoginId(@Param("loginId") String loginId);

    @Query("select l from LocationEntity l join l.userEntity u " +
            "where u.loginId=:loginId and l.id=:locationId")
    Optional<LocationEntity> findByLocationIdAndLoginId(@Param("locationId") Integer locationId,@Param("loginId") String loginId);


    // thumbnail 가져오기 용 쿼리
    @Query("select t from LocationEntity l join l.thumbnailEntities t " +
            "where l.id=:locationId order by t.thumbnailRegdate desc limit 1")
    Optional<ThumbnailEntity> findByLocationId(@Param("locationId") Integer locationId);

    @Query("select mg from MapEntity m join m.mapGroupEntity mg " +
            "where m.id=:mapId")
    Optional<MapGroupEntity> findMagGroupByMapId(@Param("mapId") Integer mapId);

    @Query("select m from MapEntity m " +
            "join m.mapGroupEntity mg join mg.locationEntity l " +
            "where l.id=:locationId and m.date=:date and m.time=:time")
    List<MapEntity> findByLocationIdAndDatetime(@Param("locationId") Integer locationId,@Param("date") LocalDate date,
                                                    @Param("time") LocalTime time);
}
