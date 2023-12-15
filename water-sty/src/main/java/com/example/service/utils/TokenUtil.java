package com.example.service.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.service.common.ErrorCode;
import com.example.service.exception.BusinessException;

import java.util.Calendar;
import java.util.Date;

public class TokenUtil {

    private static final String ISSUER = "签发者";

    public static String generateToken(Long userId) throws Exception {
        // 使用RSA256算法，传入公钥和私钥
        Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());

        // 获取当前时间并创建一个Calendar对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        // 在当前时间的基础上增加1小时
        calendar.add(Calendar.HOUR, 24*7);

        // 创建JWT并设置其KeyId为userId的字符串表示
        // 设置JWT的签发者为常量ISSUER所指定的值
        // 设置JWT的过期时间为Calendar对象所表示的时间
        // 使用算法对JWT进行签名，并返回签名后的字符串表示
        return JWT.create().withKeyId(String.valueOf(userId))
                .withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);
    }

    public static String generateRefreshToken(Long userId) throws Exception{
        Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        return JWT.create().withKeyId(String.valueOf(userId))
                .withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);
    }

    public static Long verifyToken(String token){
        try{
            // 创建RSA256算法对象，使用RSAUtil工具类获取公钥和私钥
            Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());

            // 创建JWTVerifier对象，使用之前创建的算法进行验证
            JWTVerifier verifier = JWT.require(algorithm).build();

            // 使用JWTVerifier验证传入的令牌(token)并解码为DecodedJWT对象
            DecodedJWT jwt = verifier.verify(token);

            // 从解码后的JWT中获取KeyId（通常是用户ID或标识）
            String userId = jwt.getKeyId();

            // 将获取的UserId字符串转换为Long类型并返回
            return Long.valueOf(userId);

        }catch (TokenExpiredException e){
            throw new BusinessException(ErrorCode.TOKEN_EXPIRATION_ERROR,"token过期！");

        }catch (Exception e){
            throw new BusinessException(ErrorCode.TOKEN_EXPIRATION_ERROR,"非法用户token！");

        }

    }


}
