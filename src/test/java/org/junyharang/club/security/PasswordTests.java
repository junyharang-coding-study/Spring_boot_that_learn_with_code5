package org.junyharang.club.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest public class PasswordTests {

    @Autowired private PasswordEncoder passwordEncoder;

    @Test void 이용자_비밀번호_암호화() {

        // Given
        String pwd = "11111";

        // When
        String enPwd = passwordEncoder.encode(pwd);

        // Then
        System.out.println("암호화 된 패스워드 문자열 : " + enPwd);

        boolean matchResult = passwordEncoder.matches(pwd, enPwd);

        System.out.println("matchResult : " + matchResult);

    } // 이용자_비밀번호_암호화() 끝

} // class 끝
