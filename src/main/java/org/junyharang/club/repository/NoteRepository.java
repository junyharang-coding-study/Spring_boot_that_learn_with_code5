package org.junyharang.club.repository;


import org.junyharang.club.entity.Note;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    @EntityGraph(attributePaths = "writer", type = EntityGraph.EntityGraphType.LOAD)
    @Query("select n from Note n where n.num =:num")
    Optional<Note> getWithWriter(Long num);

    @EntityGraph(attributePaths = {"writer"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select n from Note n where n.writer.email =:email")
    List<Note> getList(String email);


} // interface 끝
