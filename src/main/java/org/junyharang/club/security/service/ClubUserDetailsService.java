package org.junyharang.club.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junyharang.club.entity.ClubMember;
import org.junyharang.club.repository.ClubMemberRepository;
import org.junyharang.club.security.dto.ClubAuthMemberDTO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2 @RequiredArgsConstructor
@Service public class ClubUserDetailsService implements UserDetailsService {

    private final ClubMemberRepository clubMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("ClubUserDetailsService가 동작 중이며, loadUserByUsername Method에서 이용자 계정 : " + username + "을 찾았습니다!");

        Optional<ClubMember> result = clubMemberRepository.findByEmail(username, false);

        if(result.isPresent() == false) {
            throw new UsernameNotFoundException("Email 주소를 확인하거나, 소셜 로그인을 확인 해 주세요!");
        } // if문 끝

        ClubMember clubMember = result.get();

        log.info("------------------------------------");
        log.info(clubMember);

        ClubAuthMemberDTO clubAuthMemberDTO = new ClubAuthMemberDTO(
                clubMember.getEmail(),
                clubMember.getPassword(),
                clubMember.isFromSocial(),
                clubMember.getRoleSet().stream().map(clubMemberRole ->
                        new SimpleGrantedAuthority("ROLE_" + clubMemberRole.name())
                ).collect(Collectors.toSet()));

        clubAuthMemberDTO.setName(clubMember.getName());
        clubAuthMemberDTO.setFromSocial(clubAuthMemberDTO.isFromSocial());

        return clubAuthMemberDTO;
    } // loadUserByUsername() 끝
} // class 끝
