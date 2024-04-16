package com.example.service.constant;

/**
 * Author: yin7331
 * Date: 2023/12/30 21:39
 * Describe:
 */
public interface VideoConstant {

//    0-未删 1-已删
    /**
     * 0-未删
     */
    Integer DELETE_NO=0;
    /**
     * 1-已删
     */
    Integer DELETE_YES=1;
//    0-未审核 1-已审核 2-未通过
    Integer REVIEW_NO=0;
    Integer REVIEW_YES=1;
    Integer REVIEW_REFUSE=2;
//    0-正常 1-异常
    Integer STATUS_NORMAL=0;
    Integer STATUS_UNNORMAL=1;

//    '0-正常 1-被举报 2-犯规',
    Integer REPORT_NORMAL=0;
    Integer REPORT_REPORTED=1;
    Integer REPORT_UNNORMAL=2;

}
