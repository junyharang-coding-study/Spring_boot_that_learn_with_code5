package org.junyharang.club.repository;

import org.junit.jupiter.api.Test;
import org.junyharang.club.entity.ClubMember;
import org.junyharang.club.entity.base.ClubMemberRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest class ClubMemberRepositoryTest {

    @Autowired private ClubMemberRepository clubMemberRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Test public void 회원_가입() {
        // 1 ~ 80까지는 USER 권한만 지정
        // 81 ~ 90 까지는 USER,MANAGER
        // 91 ~ 100까지는 USER,MANAGER,ADMIN

        IntStream.rangeClosed(1, 100).forEach(i -> {
            ClubMember clubMember = ClubMember.builder()
                    .email("user"+i+"@junyharang.com")
                    .name("홍길동"+i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();

            // 기본 권한
            clubMember.addMemberRole(ClubMemberRole.USER);

            if (i > 80) {
                clubMember.addMemberRole(ClubMemberRole.MANAGER);
            }

            if (i > 90) {
                clubMember.addMemberRole(ClubMemberRole.ADMIN);
            }

            clubMemberRepository.save(clubMember);
        });
    } // 회원_가입() 끝

    @Test public void 회원_조회() {
        // given
        Optional<ClubMember> result = clubMemberRepository.findByEmail("user95@junyharang.com", false);

        // when
            ClubMember clubMember = result.get();

        // then
        System.out.println(clubMember);

    } // 회원_조회() 끝

} // class 끝