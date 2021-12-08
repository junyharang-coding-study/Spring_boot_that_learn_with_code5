package org.junyharang.club.security.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JWTUtilTest {

    private JWTUtil jwtUtil;

    @BeforeEach public void testBefore() {
        System.out.println("testBefore()가 동작하였습니다!");

        jwtUtil = new JWTUtil();
    } // testBefore() 끝

    @Test public void testEncode() throws Exception {
        String email = "user95@junyharang.com";
        String str = jwtUtil.generateToken(email);

        System.out.println(str);
    } // testEncode() 끝

    @Test public void 검증_확인() throws Exception {
        String email = "user95@junyharang.com";

        String str = jwtUtil.generateToken(email);

        Thread.sleep(5000);

        String resultEmail = jwtUtil.validateAndExtract(str);

        System.out.println(resultEmail);
    } // 검증_확인() 끝

} // class 끝