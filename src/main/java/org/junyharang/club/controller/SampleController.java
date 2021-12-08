package org.junyharang.club.controller;


import lombok.extern.log4j.Log4j2;
import org.junyharang.club.security.dto.ClubAuthMemberDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller @RequestMapping("/sample")
public class SampleController {

    @PreAuthorize("permitAll()") @GetMapping("/all") public void exAll() {
        log.info("exAll()이 작동 중 입니다!");
    } // exAll() 끝

    @GetMapping("/member") public void exMember() {
        log.info("exMember()가 작동 중 입니다!");
    } // exMember() 끝

    @PreAuthorize("hasRole('ADMIN')") @GetMapping("/admin") public void exAdmin() {
        log.info("exAdmin()이 작동 중 입니다!");
    } // exAdmin() 끝

    @PreAuthorize("#clubAuthMember != null && #clubAuthMember.username eq \"user95@junyharang.com\"")
    @GetMapping("/exOnly") public String exMemberOnly(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember) {
        log.info("exMemberOnly()가 동작 중 입니다!");
        log.info(clubAuthMember);

        return "/sample/admin";
    } // exMemberOnly() 끝
} // class 끝
