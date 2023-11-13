package com.example.controller.Support;

import com.example.service.utils.TokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Author: yin7331
 * Date: 2023/10/25 0:16
 * Describe:
 */
@Component
public class UserSupport {

    public Long getCurrentUserId(){

        // todo 后面修改过来
        // ServletRequestAttributes current=(ServletRequestAttributes)
        //         RequestContextHolder.getRequestAttributes();
        // HttpServletRequest request = current.getRequest();
        // String token = request.getHeader("token");
        // Long userId =  TokenUtil.verifyToken(token) ;
        Long userId =  123123L ;
        return userId;
    }
}
