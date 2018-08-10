package com.example.easynotes.controller;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.BedAssign;
import com.example.easynotes.repository.BedAssignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by rajeevkumarsingh on 27/06/17.
 */
@RestController
@RequestMapping("/api")
public class BedAssignController {

    @Autowired
    BedAssignRepository noteRepository;

    @GetMapping("/notes")
    public List<BedAssign> getAllNotes() {
        return noteRepository.findAll();
    }

    @PostMapping("/notes")
    public BedAssign createNote(@Valid @RequestBody BedAssign note) {
        return noteRepository.save(note);
    }

    @GetMapping("/notes/{id}")
    public BedAssign getNoteById(@PathVariable(value = "id") Long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
    }

    @PutMapping("/notes/{id}")
    public BedAssign updateNote(@PathVariable(value = "id") Long noteId,
                                           @Valid @RequestBody BedAssign noteDetails) {

    	BedAssign note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());

        BedAssign updatedNote = noteRepository.save(note);
        return updatedNote;
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
    	BedAssign note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        noteRepository.delete(note);

        return ResponseEntity.ok().build();
    }
}
