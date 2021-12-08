package org.junyharang.club.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junyharang.club.dto.NoteDTO;
import org.junyharang.club.entity.Note;
import org.junyharang.club.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2 @RequiredArgsConstructor
@Service public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public Long register(NoteDTO noteDTO) {

        Note note = dtoToEntity(noteDTO);

        log.info("================================");
        log.info(note);

        noteRepository.save(note);

        return note.getNum();
    } // register() 끝

    @Override
    public NoteDTO get(Long num) {
        Optional<Note> result = noteRepository.getWithWriter(num);

        if (result.isPresent()) {
            return entityToDTO(result.get());
        } // if문 끝

        return null;
    } // get() 끝

    @Override
    public void modify(NoteDTO noteDTO) {

        Long num = noteDTO.getNum();

        Optional<Note> result = noteRepository.findById(num);

        if (result.isPresent()) {
            Note note = result.get();

            note.changeTitle(noteDTO.getTitle());
            note.changeContent(noteDTO.getContent());

            noteRepository.save(note);
        } // if문 끝

    } // modify() 끝

    @Override
    public void remove(Long num) {
        noteRepository.deleteById(num);
    } // remove 끝

    @Override
    public List<NoteDTO> getAllWithWriter(String writerEmail) {
        List<Note> noteList = noteRepository.getList(writerEmail);

        return noteList.stream().map(note -> entityToDTO(note)).collect(Collectors.toList());
    } // getAllWithWriter() 끝
} // class 끝
