package com.example.sirius_restapi.map;

import com.example.sirius_restapi.exception.BaseResponse;
import com.example.sirius_restapi.map.domain.PatchLocationReq;
import com.example.sirius_restapi.map.domain.PostLocationReq;
import com.example.sirius_restapi.utils.SiriusUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class MapController {
    private MapService mapService;
    @GetMapping("api/report/{login_id}/locations")
    @ResponseBody
    public BaseResponse getLocations(@PathVariable String login_id) {
        return mapService.getLocations(login_id);
    }

    @GetMapping("api/report/{login_id}/locations/{location_id}")
    @ResponseBody
    public BaseResponse getLocationById(@PathVariable String login_id, @PathVariable Integer location_id) {
        return mapService.getLocationById(location_id,login_id);
    }

    @PostMapping("api/report/{login_id}/locations")
    @ResponseBody
    public BaseResponse postLocation(@PathVariable String login_id, @Valid @RequestBody PostLocationReq postLocationReq) {
        return mapService.postLocation(postLocationReq,login_id);
    }

    @PatchMapping("api/report/{login_id}/locations/{location_id}")
    @ResponseBody
    public BaseResponse patchLocation(@PathVariable String login_id, @PathVariable Integer location_id,
                                      @RequestBody PatchLocationReq patchLocationReq) {
        return mapService.patchLocation(patchLocationReq,location_id,login_id,false);
    }

    @GetMapping("api/report/{login_id}/locations/{location_id}/maps")
    @ResponseBody
    public BaseResponse getMapsByLocationId(@PathVariable String login_id, @PathVariable Integer location_id,
                @RequestParam(required = false) String date, @RequestParam(required = false) Integer time) {
        return mapService.getMapsByLocationId(location_id,login_id,date,time);
    }

    @GetMapping("api/report/maps/{map_id}/files")
    public ResponseEntity<InputStreamResource> getMapFileById(@PathVariable Integer map_id) throws IOException {
        Resource file =mapService.getMapFileById(map_id);
        return SiriusUtils.getFile(file, false);
    }

    @GetMapping("api/report/{login_id}/locations/{location_id}/thumbnails")
    public ResponseEntity<InputStreamResource> getLocationThumbnail(@PathVariable("login_id") String login_id, @PathVariable("location_id") Integer location_id) throws IOException {
        Resource file = mapService.getLocationThumbnail(location_id);
        return SiriusUtils.getFile(file, true);
    }


}
