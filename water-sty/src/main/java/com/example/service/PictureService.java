package com.example.service;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

/**
 * Author: yin7331
 * Date: 2023/12/9 9:16
 * Describe:
 */
public interface PictureService {
    void saveVideoPicture(HttpServletRequest req);

    InputStream getPictureUrl(String bucketName, String objectName);
}
