package com.example.sirius_restapi.map;

import com.example.sirius_restapi.exception.AppException;
import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.exception.ErrorCode;
import com.example.sirius_restapi.map.domain.GetMapsRes;
import com.example.sirius_restapi.map.domain.LocationEntity;
import com.example.sirius_restapi.map.domain.MapEntity;
import com.example.sirius_restapi.map.domain.ThumbnailEntity;
import com.example.sirius_restapi.user.UserService;
import com.example.sirius_restapi.utils.SiriusUtils;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.sirius_restapi.map.domain.QMapEntity.mapEntity;


@Service
@AllArgsConstructor
public class MapService {
    @Autowired
    private JPAQueryFactory queryFactory;
    private MapRepository mapRepository;
    private UserService userService;

    public BaseResponse getLocations(Integer userId) {
        return new BaseResponse(ErrorCode.SUCCESS, mapRepository.findAllByUserId(userId));
    }

    public BaseResponse getLocationById(Integer locationId, Integer userId) {
        return new BaseResponse(ErrorCode.SUCCESS, mapRepository.findByLocationIdAndUserId(locationId, userId)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND)));
    }

    public BaseResponse getMpasByLocationId(Integer locationId, Integer userId, String date, Integer time) {
        userService.getUserById(userId);

        JPAQuery<MapEntity> query = queryFactory.selectFrom(mapEntity)
                .where(mapEntity.locationEntity.id.eq(locationId));

        if (date != null) {
            LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
            query.where(mapEntity.date.eq(parsedDate));
        }

        if (time != null) {
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

    public Resource getMpaFileById(Integer mapId) {
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
