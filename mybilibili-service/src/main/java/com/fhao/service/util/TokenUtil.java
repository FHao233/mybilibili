package com.fhao.service.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fhao.domain.exception.ConditionException;

import java.util.Calendar;
import java.util.Date;

/**
 * author: FHao
 * create time: 2023-03-19 13:47
 * description:
 */
public class TokenUtil {
    private static final String ISSUER = "FHao";

    public static String generateToken(Long userId) throws Exception {

        Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, 1);
        return JWT.create().withKeyId(String.valueOf(userId)).
                withIssuer(ISSUER).
                withExpiresAt(calendar.getTime()).sign(algorithm);
    }

    public static Long verifyToken(String token) {
        //不可以抛出异常，因为失败可能因为token过期的原因，需要特殊处理
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            String userId = jwt.getKeyId();
            return Long.valueOf(userId);
        } catch (TokenExpiredException e) {
            throw new ConditionException("555", "token过期！");
        } catch (Exception e) {
            throw new ConditionException("非法用户token！");
        }
    }

    public static String generateRefreshToken(Long userId) throws Exception {
        Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        return JWT.create().withKeyId(String.valueOf(userId)).
                withIssuer(ISSUER).
                withExpiresAt(calendar.getTime()).sign(algorithm);
    }

    public static void main(String[] args) throws Exception {
        String s = TokenUtil.generateRefreshToken(1L);
        System.out.println(s);
    }
}
