package konkon.service.impl;

import konkon.model.Note;
import konkon.model.NoteForm;
import konkon.model.NoteType;
import konkon.repository.NoteRepository;
import konkon.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class NoteServiceImpl implements NoteService {
  @Autowired
  private NoteRepository noteRepository;


  @Override
  public void delete(Long id) {
    noteRepository.delete(id);
  }

  @Override
  public void save(NoteForm noteForm) {
    Note note = getNote(noteForm);
    noteRepository.save(note);
  }

  @Override
  public Note getNote(NoteForm noteForm) {
    if(noteForm.getId() == null){
      return new Note(noteForm.getTitle(),noteForm.getContent(),noteForm.getNoteType());
    }else{
      return new Note(noteForm.getId(),noteForm.getTitle(),noteForm.getContent(),noteForm.getNoteType());
    }
  }

  @Override
  public Note findById(Long id) {
   return noteRepository.findOne(id);
  }

  @Override
  public Page<Note> findAll(Pageable pageable) {
    return noteRepository.sort(pageable);
  }

  @Override
  public Page<Note> findAllByNoteTypeId(Long id, Pageable pageable) {
    return noteRepository.findAllByNoteTypeId(id,pageable);
  }


  @Override
  public Page<Note> findAllByTitleContaining(String title, Pageable pageable) {
    return noteRepository.findAllByTitleContaining(title,pageable);
  }
}
