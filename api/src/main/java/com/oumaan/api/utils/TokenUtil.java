package com.oumaan.api.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;

import java.util.Date;
import java.util.Map;

/**
 * @Author: wjj
 * @Date: 2023/12/15
 * Token工具类
 */
public class TokenUtil {
    //设置过期时间
    private static final long EXPIRE_DATE = 24 * 60 * 60 * 1000;
    //token秘钥
    private static final String TOKEN_SECRET = "OUMAAN2023";

    /**
     * 生成token
     *
     * @param username
     * @return
     */
    public static String token(String username) {
        //过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_DATE);
        //秘钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        //设置头部信息
        Map<String, Object> header = Maps.newHashMap();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        //携带username，password信息，生成签名
        return JWT.create()
                .withHeader(header)
                .withClaim("username", username)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 验证token，返回username
     *
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaim("username").asString();
        } catch (Exception e) {
            return null;
        }
    }
}
