package org.junyharang.club.security.handler;

import lombok.extern.log4j.Log4j2;
import org.junyharang.club.security.dto.ClubAuthMemberDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class ClubLoginSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private PasswordEncoder passwordEncoder;

    public ClubLoginSuccessHandler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    } // 생성자 끝

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

       log.info("--------------------------------------------");
       log.info("이용자의 인증이 성공하였습니다!");

        ClubAuthMemberDTO authMember = (ClubAuthMemberDTO) authentication.getPrincipal();

        boolean fromSocial = authMember.isFromSocial();

        log.info("회원 정보 수정 필요 여부 : " + fromSocial);

        boolean passwordResult = passwordEncoder.matches("1111", authMember.getPassword());

        if (fromSocial && passwordResult) { // 소셜 로그인 회원 중 Password가 1111이라면?
            // 회원 정보 수정 페이지로 이동
            redirectStrategy.sendRedirect(request, response, "/member/modify?from=social");
        } // if 문 (소셜 로그인 회원 중 Password가 1111이라면?) 끝
    } // onAuthenticationSuccess() 끝
} // class 끝
