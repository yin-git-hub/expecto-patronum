package com.example.controller;

import com.example.service.PictureService;
import com.example.service.common.BaseResponse;
import com.example.service.common.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * Author: yin7331
 * Date: 2023/12/9 9:12
 * Describe:
 */
@RestController
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    PictureService pictureService;

    @PostMapping("/upload")
    public BaseResponse pictureUpload(HttpServletRequest req){
        pictureService.saveVideoPicture( req);
        return ResultUtils.success();
    }

    @GetMapping("/{bucketName}/{objectName}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String bucketName, @PathVariable String objectName) throws IOException {

        InputStream pictureUrl = pictureService.getPictureUrl(bucketName, objectName);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Set the appropriate content type

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(pictureUrl));

    }

}
