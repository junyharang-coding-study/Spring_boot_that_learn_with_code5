package org.junyharang.club.security.filter;

import lombok.extern.log4j.Log4j2;
import org.junyharang.club.security.dto.ClubAuthMemberDTO;
import org.junyharang.club.security.util.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {

    private JWTUtil jwtUtil;

    public ApiLoginFilter(String defaultFilterProcessesUrl, JWTUtil jwtUtil) {
        super(defaultFilterProcessesUrl);
        this.jwtUtil = jwtUtil;
    } // 생성자 끝

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        log.info("--------------------API Login Filter가 동작 중 입니다!");
        log.info("attemptAuthentication()가 동작 중 입니다!");

        String email = request.getParameter("email");
        String pw = request.getParameter("pw");

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, pw);

        return getAuthenticationManager().authenticate(authToken);

    } // attemptAuthentication() 끝

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        log.info("---------------APiLoginFilter가 동작하였습니다!----------------------");
        log.info("인증에 성공한 내용 : " + authResult);

        log.info(authResult.getPrincipal());

        // email address
        String email = ((ClubAuthMemberDTO) authResult.getPrincipal()).getUsername();

        String token = null;

        try {
            token = jwtUtil.generateToken(email);

            response.setContentType("/text/plain");
            response.getOutputStream().write(token.getBytes());

            log.info(token);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} // class 끝
