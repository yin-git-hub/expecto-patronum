package com.example.service;

import com.example.dao.model.entity.Scrolling;
import org.springframework.scheduling.annotation.Async;

/**
 * Author: yin7331
 * Date: 2023/11/3 5:54
 * Describe:
 */
public interface ScrollingService {
    public void saveScroller(Scrolling scrolling);
    public void testSaveScroller(Scrolling scrolling);

}
