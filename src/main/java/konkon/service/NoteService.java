package konkon.service;

import konkon.model.Note;
import konkon.model.NoteForm;
import konkon.model.NoteType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface NoteService {
  void delete(Long id);
  void save(NoteForm noteForm) throws IOException;
  Note getNote(NoteForm noteForm);

  Note findById(Long id);
  List<Note> findAll();
  Page<Note> findAll(Pageable pageable);
  Page<Note> findAllByNoteTypeId(Long id, Pageable pageable);
  Page<Note> findAllByTitleContaining(String title, Pageable pageable);
  void saveNoteInFileTxt();
  void writeJSON();
  void importJSON();
}
