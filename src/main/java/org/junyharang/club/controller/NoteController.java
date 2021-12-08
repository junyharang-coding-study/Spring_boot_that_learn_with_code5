package org.junyharang.club.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junyharang.club.dto.NoteDTO;
import org.junyharang.club.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2 @RequiredArgsConstructor @RequestMapping("/notes/")
@RestController public class NoteController {

    private final NoteService noteService;

    @PostMapping(value = "")
    public ResponseEntity<Long> register(@RequestBody NoteDTO noteDTO) {

        log.info("---------------Controller의 register()가 동작합니다!----------------------");
        log.info(noteDTO);

        Long num = noteService.register(noteDTO);

        return new ResponseEntity<>(num, HttpStatus.OK);

    } // register() 끝

    @GetMapping(value = "{num}", produces = MediaType.APPLICATION_JSON_VALUE)   //응답 시 사용 포맷을 JSON으로 설정
    public ResponseEntity<NoteDTO> read(@PathVariable("num") Long num) {
        log.info("----------------------Controller의 read()가 동작합니다!--------------------------");
        log.info(num);

        return new ResponseEntity<>(noteService.get(num), HttpStatus.OK);
    } // read () 끝


    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)    //응답 시 사용 포맷을 JSON으로 설정
    public ResponseEntity<List<NoteDTO>> getList(String email) {

        log.info("----------------controller의 getList()가 동작합니다!---------------------------");
        log.info(email);

        return new ResponseEntity<>(noteService.getAllWithWriter(email), HttpStatus.OK);

    } // getList() 끝

    @PatchMapping(value = "{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> modify(@RequestBody NoteDTO noteDTO) {
        log.info("----------------contorller의 modify()가 동작합니다!------------------------");
        log.info(noteDTO);

        noteService.modify(noteDTO);

        return new ResponseEntity<>("수정이 완료 되었습니다!", HttpStatus.OK);
    } // modify() 끝

    @DeleteMapping(value = "{num}", produces = MediaType.TEXT_PLAIN_VALUE)  //응답 시 사용 포맷을 문자열로 설정
    public ResponseEntity<String> remove(@PathVariable("num") Long num) {
        log.info("---------------------controller의 remove()가 동작합니다!---------------------------");
        log.info(num);

        noteService.remove(num);

        return new ResponseEntity<>("삭제가 완료 되었습니다!", HttpStatus.OK);
    } // remove() 끝



} // class 끝
