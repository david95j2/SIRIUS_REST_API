package com.example.sirius_restapi.map;

import com.example.sirius_restapi.exception.AppException;
import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.exception.ErrorCode;
import com.example.sirius_restapi.map.domain.*;
import com.example.sirius_restapi.user.UserService;
import com.example.sirius_restapi.user.domain.UserEntity;
import com.example.sirius_restapi.utils.SiriusUtils;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.sirius_restapi.map.domain.QMapEntity.mapEntity;


@Service
@AllArgsConstructor
public class MapService {
    private JPAQueryFactory queryFactory;
    private MapRepository mapRepository;
    private LocationRepository locationRepository;
    private UserService userService;

    public BaseResponse getLocations(String loginId) {
        return new BaseResponse(ErrorCode.SUCCESS, mapRepository.findAllByLoginId(loginId));
    }

    public BaseResponse getLocationById(Integer locationId, String loginId) {
        return new BaseResponse(ErrorCode.SUCCESS, mapRepository.findByLocationIdAndLoginId(locationId, loginId)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND)));
    }

    public BaseResponse postLocation(@Valid PostLocationReq postLocationReq, String loginId) {
        UserEntity userEntity =(UserEntity) userService.getUserByLoginId(loginId).getResult();

        // location 장소가 이미 있으면 위도,경도 update
        LocationEntity comparedLocation = locationRepository.findByLocation(postLocationReq.getLocation()).orElse(null);
        if (postLocationReq.getLocation() == comparedLocation.getLocation()) {
            PatchLocationReq patchLocationReq = new PatchLocationReq();
            patchLocationReq.setLatitude(postLocationReq.getLatitude());
            patchLocationReq.setLongitude(postLocationReq.getLongitude());
            return patchLocation(patchLocationReq, comparedLocation.getId(),loginId);
        } else { // location 장소가 없으면 post
            LocationEntity locationEntity = LocationEntity.from(postLocationReq,userEntity);
            Integer location_id = locationRepository.save(locationEntity).getId();
            return new BaseResponse(ErrorCode.SUCCESS,Integer.valueOf(location_id)+"번 장소가 생성되었습니다.");
        }
    }

    public BaseResponse patchLocation(PatchLocationReq patchLocationReq, Integer locationId, String loginId) {
        LocationEntity locationEntity = locationRepository.findByIdAndLoginId(locationId,loginId).orElseThrow(
                () -> new AppException(ErrorCode.DATA_NOT_FOUND)
        );
        if (patchLocationReq.getLocation() != null) {
            locationEntity.setLocation(patchLocationReq.getLocation());
        }
        if (patchLocationReq.getLatitude() != null) {
            locationEntity.setLatitude(patchLocationReq.getLatitude());
        }
        if (patchLocationReq.getLongitude() != null) {
            locationEntity.setLongitude(patchLocationReq.getLongitude());
        }

        LocationEntity updated = locationRepository.save(locationEntity);
        PatchLocationRes patchLocationRes = updated.toDto();
        return new BaseResponse(ErrorCode.CREATED,patchLocationRes);
    }

    public BaseResponse getMapsByLocationId(Integer locationId, String loginId, String date, Integer time) {

        userService.getUserByLoginId(loginId);

        JPAQuery<MapEntity> query = queryFactory.selectFrom(mapEntity)
                .where(mapEntity.mapGroupEntity.locationEntity.id.eq(locationId));

        if (date != null) {
            LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
            query.where(mapEntity.date.eq(parsedDate));
        }

        if (time != null) {
            if (time >= 24) {
                throw new AppException(ErrorCode.TIME_METHOD_NOT_ALLOWED);
            }
            LocalTime startTime = LocalTime.of(time, 0);
            LocalTime endTime = LocalTime.of(23, 59, 59);
            query.where(mapEntity.time.between(startTime, endTime));
        }

        List<MapEntity> results = query.fetch();

        List<GetMapsRes> new_results = results.stream().map(x -> {
            String fileName = Paths.get(x.getMapPath()).getFileName().toString();
            GetMapsRes getMapsRes = new GetMapsRes();
            getMapsRes.setId(x.getId());
            getMapsRes.setFile_path(fileName);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime combinedDateTime = LocalDateTime.of(x.getDate(), x.getTime());
            String formattedDate = combinedDateTime.format(formatter);
            getMapsRes.setRegdate(formattedDate);
            return getMapsRes;
        }).collect(Collectors.toList());

        return new BaseResponse(ErrorCode.SUCCESS, new_results);
    }

    public void postMaps(PostMapReq postMapReq, Integer locationId) {
        //
    }

    public Resource getMapFileById(Integer mapId) {
        MapEntity mapEntity = mapRepository.findById(mapId).orElseThrow(()-> new AppException(ErrorCode.DATA_NOT_FOUND));
        return SiriusUtils.loadFileAsResource(Paths.get(mapEntity.getMapPath()).getParent().toString(),
                Paths.get(mapEntity.getMapPath()).getFileName().toString());
    }

    public Resource getLocationThumbnail(Integer locationId) {
        ThumbnailEntity thumbnailEntity = mapRepository.findByLocationId(locationId).orElseThrow(()-> new AppException(ErrorCode.DATA_NOT_FOUND));

        return SiriusUtils.loadFileAsResource(Path.of(thumbnailEntity.getThumbnailPath()).getParent().toString().replace("\\","/"),
                Path.of(thumbnailEntity.getThumbnailPath()).getFileName().toString());
    }



}
