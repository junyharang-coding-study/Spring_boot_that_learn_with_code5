package org.junyharang.club.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor @AllArgsConstructor @Builder @Data
public class NoteDTO {

    private Long num;

    private String title;

    private String content;
    private String writerEmail;

    private LocalDateTime regDate, modDate;

} // class 끝
