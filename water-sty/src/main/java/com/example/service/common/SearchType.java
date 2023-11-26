package com.example.service.common;

/**
 * Author: yin7331
 * Date: 2023/11/21 12:29
 * Describe:
 */
public enum SearchType {
    //随笔
    Essay ("essay"),
    //文章
    Artical("artical"),
    VideoInfo("video"),
    UserInfo("user"),

    Scrolling("scrolling")
    ;
    private String type;
    SearchType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static Boolean VerifyType(String type){
        for (SearchType value : SearchType.values()) {
            if (value.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
