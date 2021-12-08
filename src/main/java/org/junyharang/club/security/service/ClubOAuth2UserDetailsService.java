package org.junyharang.club.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junyharang.club.entity.ClubMember;
import org.junyharang.club.entity.base.ClubMemberRole;
import org.junyharang.club.repository.ClubMemberRepository;
import org.junyharang.club.security.dto.ClubAuthMemberDTO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2 @RequiredArgsConstructor
@Service public class ClubOAuth2UserDetailsService extends DefaultOAuth2UserService {

    private final ClubMemberRepository clubMemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("------------------------------------");
        //org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest 객체 출력
        log.info("이용자 로그인 요청 정보 : " + userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();

        // google로 출력
        log.info("clientName" + clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("=======================================");

        oAuth2User.getAttributes().forEach((key, value) -> {
            // 이용자의 sub, picture, email, email_verified, 정보를 받아 출력
            log.info(key + " : " + value);
        });

        String email = null;

        if (clientName.equals("Google")) {  // 구글을 이용한다면?
            email = oAuth2User.getAttribute("email");
        } // 구글 이용 여부 확인 if문 끝

//        log.info("이용자 EMail 정보 : " + email);
//
        ClubMember member = saveSocialMember(email);
//
//        return oAuth2User;

        ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO(
                member.getEmail(),
                member.getPassword(), true,
                member.getRoleSet().stream().map(
                        clubMemberRole -> new SimpleGrantedAuthority("ROLE_" + clubMemberRole.name())).collect(Collectors.toList()), oAuth2User.getAttributes());

        clubAuthMember.setName(member.getName());

        return clubAuthMember;
    } // loadUser() 끝

    private ClubMember saveSocialMember(String email) {
        // ClubMemberRepository를 이용 소셜 로그인한 Email 처리

        // 기존 동일 Email로 가입한 회원이 있는 경우 조회만
        Optional<ClubMember> result = clubMemberRepository.findByEmail(email, true);

        if (result.isPresent()) { // result에 값이 있다면?
            // result에 있는 값을 가져와라
            return result.get();
        } // if문 끝

        // result에 값이 없다면 회원 추가 패스워드는 1111 이름은 Email 주소로
        ClubMember clubMember = ClubMember.builder()
                .email(email)
                .name(email)
                .password("1111")
                .fromSocial(true)
                .build();

        clubMember.addMemberRole(ClubMemberRole.USER);

        clubMemberRepository.save(clubMember);

        return clubMember;
    } // saveSocialMember() 끝
} // class 끝
