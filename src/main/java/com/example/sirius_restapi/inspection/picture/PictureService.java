package com.example.sirius_restapi.inspection.picture;

import com.example.sirius_restapi.exception.AppException;
import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.exception.ErrorCode;
import com.example.sirius_restapi.inspection.picture.domain.GetPictureRes;
import com.example.sirius_restapi.inspection.picture.domain.PictureEntity;
import com.example.sirius_restapi.utils.SiriusUtils;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.sirius_restapi.inspection.picture.domain.QPictureEntity.pictureEntity;

@Service
@AllArgsConstructor
public class PictureService {
    private JPAQueryFactory queryFactory;
    private PictureRepository pictureRepository;

    public BaseResponse getPicturesByInspectId(Integer inspectionId, String date, Integer time) {
        JPAQuery<PictureEntity> query = queryFactory.selectFrom(pictureEntity)
                .leftJoin(pictureEntity.pictureLocationEntity).fetchJoin()
                .where(pictureEntity.inspectionEntity.id.eq(inspectionId));

        if (date != null) {
            LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
            query.where(pictureEntity.date.eq(parsedDate));
        }

        if (time != null) {
            if (time >= 24) {
                throw new AppException(ErrorCode.DATA_NOT_FOUND);
            }
            LocalTime startTIme = LocalTime.of(time,0);
            LocalTime endTime = LocalTime.of(23,59,59);
            query.where(pictureEntity.time.between(startTIme,endTime));
        }

        List<PictureEntity> results = query.fetch();

        List<GetPictureRes> new_results = results.stream().map(x -> {
            String fileName = Paths.get(x.getFilePath()).getFileName().toString();
            GetPictureRes getPictureRes = new GetPictureRes();
            getPictureRes.setId(x.getId());
            getPictureRes.setFile_name(fileName);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime combinedDateTime = LocalDateTime.of(x.getDate(), x.getTime());
            String formattedDate = combinedDateTime.format(formatter);
            getPictureRes.setRegdate(formattedDate);
            getPictureRes.setPos_x(x.getPictureLocationEntity().getPosX());
            getPictureRes.setPos_y(x.getPictureLocationEntity().getPosY());
            getPictureRes.setPos_z(x.getPictureLocationEntity().getPosZ());
            getPictureRes.setRoll(x.getPictureLocationEntity().getRoll());
            getPictureRes.setPitch(x.getPictureLocationEntity().getPitch());
            getPictureRes.setYaw(x.getPictureLocationEntity().getYaw());
            return getPictureRes;
        }).collect(Collectors.toList());
        return new BaseResponse(ErrorCode.SUCCESS,new_results);
    }

    public Resource getPictureFileById(Integer pictureId) {
        PictureEntity pictureEntity = pictureRepository.findById(pictureId).orElseThrow(
                ()-> new AppException(ErrorCode.DATA_NOT_FOUND)
        );
        return SiriusUtils.loadFileAsResource(Paths.get(pictureEntity.getFilePath()).getParent().toString(),
                Paths.get(pictureEntity.getFilePath()).getFileName().toString());
    }
}
