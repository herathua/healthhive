package io.bootify.health_hive.service;

import io.bootify.health_hive.domain.Notes;
import io.bootify.health_hive.domain.User;
import io.bootify.health_hive.model.NotesDTO;
import io.bootify.health_hive.repos.NotesRepository;
import io.bootify.health_hive.repos.UserRepository;
import io.bootify.health_hive.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class NotesService {

    private final NotesRepository notesRepository;
    private final UserRepository userRepository;

    public NotesService(final NotesRepository notesRepository,
                        final UserRepository userRepository) {
        this.notesRepository = notesRepository;
        this.userRepository = userRepository;
    }

    public List<NotesDTO> findAll() {
        final List<Notes> noteses = notesRepository.findAll(Sort.by("id"));
        return noteses.stream()
                .map(notes -> mapToDTO(notes, new NotesDTO()))
                .toList();
    }

    public NotesDTO get(final Long id) {
        return notesRepository.findById(id)
                .map(notes -> mapToDTO(notes, new NotesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final NotesDTO notesDTO) {
        final Notes notes = new Notes();
        mapToEntity(notesDTO, notes);
        return notesRepository.save(notes).getId();
    }

    public void update(final Long id, final NotesDTO notesDTO) {
        final Notes notes = notesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(notesDTO, notes);
        notesRepository.save(notes);
    }

    public void delete(final Long id) {
        notesRepository.deleteById(id);
    }

    private NotesDTO mapToDTO(final Notes notes, final NotesDTO notesDTO) {
        notesDTO.setId(notes.getId());
        notesDTO.setNotes(notes.getNotes());
        notesDTO.setDate(notes.getDate());
        notesDTO.setUser(notes.getUser() == null ? null : notes.getUser().getId());
        return notesDTO;
    }

    private Notes mapToEntity(final NotesDTO notesDTO, final Notes notes) {
        notes.setNotes(notesDTO.getNotes());
        notes.setDate(notesDTO.getDate());
        final User user = notesDTO.getUser() == null ? null : userRepository.findById(notesDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        notes.setUser(user);
        return notes;
    }

    public List<NotesDTO> findByUser(Long userId) {
        final List<Notes> noteses = notesRepository.findByUser_Id(userId);
        return noteses.stream()
                .map(notes -> mapToDTO(notes, new NotesDTO()))
                .toList();
    }
}
