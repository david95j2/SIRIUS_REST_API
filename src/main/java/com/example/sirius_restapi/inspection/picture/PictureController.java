package com.example.sirius_restapi.inspection.picture;

import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.utils.SiriusUtils;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class PictureController {
    private PictureService pictureService;

    @GetMapping("api/report/maps/{map_id}/inspections/{inspection_id}/pictures")
    @ResponseBody
    public BaseResponse getPicturesByInspectId(@PathVariable Integer map_id, @PathVariable Integer inspection_id,
                                               @RequestParam(required = false) String date, @RequestParam(required = false) Integer time) {
        return pictureService.getPicturesByInspectId(inspection_id,date,time);
    }

    @GetMapping("api/report/maps/inspections/pictures/{picture_id}/files")
    public ResponseEntity<InputStreamResource> getPictureFileById(@PathVariable Integer picture_id) throws IOException {
        Resource file = pictureService.getPictureFileById(picture_id);
        return SiriusUtils.getFile(file, false);
    }

    @GetMapping("api/report/maps/inspections/pictures/{picture_id}/files/thumbnails")
    public ResponseEntity<InputStreamResource> getPictureThumbnailById(@PathVariable Integer picture_id) throws IOException {
        Resource file = pictureService.getPictureFileById(picture_id);
        return SiriusUtils.getFile(file, true);
    }
}
