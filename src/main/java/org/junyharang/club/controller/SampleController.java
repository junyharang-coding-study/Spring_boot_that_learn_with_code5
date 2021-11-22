package org.junyharang.club.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller @RequestMapping("/sample")
public class SampleController {

    @GetMapping("/all") public void exAll() {
        log.info("exAll()이 작동 중 입니다!");
    } // exAll() 끝

    @GetMapping("/member") public void exMember() {
        log.info("exMember()가 작동 중 입니다!");
    } // exMember() 끝

    @GetMapping("/admin") public void exAdmin() {
        log.info("exAdmin()이 작동 중 입니다!");
    } // exAdmin() 끝

} // class 끝
