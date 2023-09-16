package com.example.sirius_restapi.map;

import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.user.UserService;
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
public class MapController {
    private MapService mapService;
    private UserService userService;
    @GetMapping("api/report/{user_id}/locations")
    @ResponseBody
    public BaseResponse getLocations(@PathVariable Integer user_id) {
        return mapService.getLocations(user_id);
    }

    @GetMapping("api/report/{user_id}/locations/{location_id}")
    @ResponseBody
    public BaseResponse getLocationById(@PathVariable Integer user_id, @PathVariable Integer location_id) {
        return mapService.getLocationById(location_id,user_id);
    }

    @GetMapping("api/report/{user_id}/locations/{location_id}/maps")
    @ResponseBody
    public BaseResponse getMapsByLocationId(@PathVariable Integer user_id, @PathVariable Integer location_id,
                @RequestParam(required = false) String date, @RequestParam(required = false) Integer time) {
        return mapService.getMpasByLocationId(location_id,user_id,date,time);
    }

    @GetMapping("api/report/maps/{map_id}/files")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getMapFileById(@PathVariable Integer map_id) throws IOException {
        Resource file =mapService.getMpaFileById(map_id);
        return SiriusUtils.getFile(file, false);
    }

    @GetMapping("api/report/{user_id}/locations/{location_id}/thumbnails")
    public ResponseEntity<InputStreamResource> getLocationThumbnail(@PathVariable("location_id") Integer location_id) throws IOException {
        Resource file = mapService.getLocationThumbnail(location_id);
        return SiriusUtils.getFile(file, true);
    }


}
