package com.greengiant.website.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * Created with IntelliJ IDEA
 *
 * @Author yuanhaoyue swithaoy@gmail.com
 * @Description JWT 工具类
 * @Date 2018-04-07
 * @Time 22:48
 */
@Slf4j
public class JWTUtil {
    // 过期时间 24 小时 60 * 24 * 60 * 1000
    private static final long EXPIRE_TIME = 60 * 1000;//todo 改成1分钟测试
    // 密钥
    private static final String SECRET = "SHIRO+JWT";

    /**
     * 生成 token, 5min后过期
     *
     * @param username 用户名
     * @return 加密的token
     */
    public static String createToken(String username, String password) {
//        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            // 附带username信息
            return JWT.create()
                    .withClaim("username", username)
                    .withClaim("password", password)
                    //到期时间
                    .withExpiresAt(date)
                    //创建一个新的JWT，并使用给定的算法进行标记
                    .sign(algorithm);
            //todo issuedAt和expiresAt区别是啥？
    }

    /**
     * 校验 token 是否正确
     *
     * @param token    密钥
     * @param username 用户名
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String password) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            //在token中附带了username信息
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .withClaim("password", password)
                    .build();
            //验证 token
            verifier.verify(token);

            return true;
        } catch (Exception ex) {
            log.error("verify failed, token:[{}]", token, ex);
            return false;
        }
    }

    public static boolean verify(String token, String username, String password, String salt) {
        try {
            // todo 需要判空吗？【会在getUsername为null的时候有漏洞吗？】
            if (username.equals(getUsername(token)) && password.equals(PasswordUtil.encrypt(getPassword(token), salt))) {
                // todo 没有和上面一个函数一样用verifier.verify会有安全问题吗？
                return true;
            }
        } catch (Exception ex) {
            log.error("verify failed, token:[{}]", token, ex);
            return false;
        }

        return false;
    }


    /**
     * 获得token中的信息，无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            jwt.getExpiresAt();
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException ex) {
            log.error("decode failed, token:[{}]", token, ex);
            return null;
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getPassword(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("password").asString();
        } catch (JWTDecodeException ex) {
            log.error("decode failed, token:[{}]", token, ex);
            return null;
        }
    }

    public static Date getExpiresAt(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);

            return jwt.getExpiresAt();
        } catch (JWTDecodeException ex) {
            log.error("decode failed, token:[{}]", token, ex);
            return null;
        }
    }

}
