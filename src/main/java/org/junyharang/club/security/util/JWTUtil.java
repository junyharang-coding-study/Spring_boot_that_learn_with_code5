package org.junyharang.club.security.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultJws;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.log4j.Log4j2;

import java.util.Date;
import java.time.ZonedDateTime;

@Log4j2
public class JWTUtil {

    private String secretKey = "junyharang12345678";

    // 1month
    private long expire = 60 * 24 * 30;

    public String generateToken(String content) throws Exception {
        return Jwts.builder()
                .setIssuedAt(new Date())
//                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(1).toInstant()))
                .claim("sub", content)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8"))
                .compact();
    } // generateToken() 끝

    public String validateAndExtract(String tokenStr) throws Exception {

        String contentValue = null;

        try {
            DefaultJws defaultJws = (DefaultJws) Jwts.parser().setSigningKey(secretKey.getBytes("UTF-8")).parseClaimsJws(tokenStr);

            log.info(defaultJws);
            log.info(defaultJws.getBody().getClass());

            DefaultClaims claims = (DefaultClaims) defaultJws.getBody();

            log.info("------------------------------------");

            contentValue = claims.getSubject();
        } catch (Exception e) {

            e.printStackTrace();
            log.error(e.getMessage());
            contentValue = null;

        } // try-catch 끝
        return contentValue;
    } // validateAndExtract() 끝
} // class 끝
