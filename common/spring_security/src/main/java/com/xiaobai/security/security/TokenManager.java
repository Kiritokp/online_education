package com.xiaobai.security.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 * token管理
 * </p>
 */
@Component
public class TokenManager {

    private long tokenExpiration = 24*60*60*1000; //token的有效时间
    private String tokenSignKey = "123456";  //编码秘钥

    /**
     * 根据用户名生成token
     * @param username
     * @return
     */
    public String createToken(String username) {
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    /**
     * 根据token字符串得到用户信息
     * @param token
     * @return
     */
    public String getUserFromToken(String token) {
        String user = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
        return user;
    }

    /**
     * 删除token
     * @param token
     */
    public void removeToken(String token) {
        //jwttoken无需删除，客户端扔掉即可。
    }

}
