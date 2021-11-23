package org.junyharang.club.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Log4j2
@Configuration public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 이용자의 패스워드 암호화를 위한 설정
    @Bean PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } // passwordEncoder() 끝

    /*
     configure(auth)는 임시로 만든 Method로 미사용 처리
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // 이용자 계정은 user1
//        auth.inMemoryAuthentication().withUser("user1")
//
//        // 11111 패스워드 암호화 결과
//                .password("$2a$10$sq4lPjbhbICoqgPZDT/aQOQcmx.v2qGpcVVYgxN6kyovtuvHMwzQm")
//                .roles("USER");
//    } // configure(auth) 끝

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // all url은 모든 이용자 접근 가능(비회원 포함)
                .antMatchers("/sample/all").permitAll()
                //member url은 USER 권한을 가진 이용자만 접근 가능
                .antMatchers("/sample/member").hasRole("USER");

        // 위의 인가조건과 맞지 않으면 로그인 화면으로 이동
        http.formLogin();

        // csrf공격 대비를 위한 토큰 발행 중지
        http.csrf().disable();

        // Logout 기능 추가
        http.logout();
    } //configure(http) 끝
} // class 끝
