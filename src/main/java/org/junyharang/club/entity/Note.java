package org.junyharang.club.entity;

import lombok.*;

import javax.persistence.*;

@Builder @AllArgsConstructor @NoArgsConstructor @Getter @ToString
@Entity public class Note {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClubMember writer;

    public void changeTitle(String title) {
        this.title = title;
    } // changeTitle() 끝

    public void changeContent(String content) {
        this.content = content;
    } // changeContent() 끝

} // class 끝
