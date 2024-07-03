package io.bootify.health_hive.rest;

import io.bootify.health_hive.model.NotesDTO;
import io.bootify.health_hive.service.NotesService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/notess", produces = MediaType.APPLICATION_JSON_VALUE)
public class NotesResource {

    private final NotesService notesService;

    public NotesResource(final NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping
    public ResponseEntity<List<NotesDTO>> getAllNotess() {
        return ResponseEntity.ok(notesService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotesDTO> getNotes(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(notesService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createNotes(@RequestBody @Valid final NotesDTO notesDTO) {
        final Long createdId = notesService.create(notesDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateNotes(@PathVariable(name = "id") final Long id,
                                            @RequestBody @Valid final NotesDTO notesDTO) {
        notesService.update(id, notesDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotes(@PathVariable(name = "id") final Long id) {
        notesService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
