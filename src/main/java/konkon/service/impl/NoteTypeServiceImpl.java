package konkon.service.impl;

import konkon.model.NoteType;
import konkon.repository.TypeNoteRepository;
import konkon.service.NoteTypeService;
import org.springframework.beans.factory.annotation.Autowired;

public class NoteTypeServiceImpl implements NoteTypeService {
  @Autowired
  private TypeNoteRepository typeNoteRepository;

  @Override
  public Iterable<NoteType> findAll() {
    return typeNoteRepository.findAll();
  }

  @Override
  public NoteType findById(Long id) {
    return typeNoteRepository.findOne(id);
  }
}
