package com.apishop.apibstage.myJwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */

public class JwtUtil {

    /**
     * 过期时间：15分钟   ? 过期无法控制
     */
    private static  final long EXPIRE_TIME = 60 * 60 * 1000;

    /**
     * 私钥
     */
    private static final String TOKEN_SECRET = "f26e587c28064d0e855e72c0a6a0e618";

    /**
     * 生成签名方法
     * @param username
     * @param userId
     * @return
     */
    public static String sign(String username,String userId){
        //过期时间
        Date date =new Date(System.currentTimeMillis()+ EXPIRE_TIME );
        //私钥和加密算法
        Algorithm algorithm=null;
        try {
             algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String,Object> header = new HashMap<>(2);
            header.put("type","JWT");
            header.put("alg","HS256");
            //附带Usernamm,userid信息生成签名
            return JWT.create()
                    .withHeader(header)
                    .withClaim("username",username)
                    .withClaim("userId",userId)
                    .withExpiresAt(date)
                    .sign(algorithm);

        } catch (UnsupportedEncodingException e) {
            return null;
        }

    }


    /**
     * 解析Token中用户名信息
     * @param token
     * @return
     */
    public static String getUserName(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        }catch (JWTDecodeException e){
            return null;
        }
    }

    public static int getUserId(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            String userId  = jwt.getClaim("userId").asString();
            return Integer.parseInt(userId);
        }catch (JWTDecodeException e){
            return -1;
        }
    }


    /**
     * 验证Token
     * @param token
     * @return
     */
    public static boolean verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }
}









































