package konkon.service;

import konkon.model.NoteType;

public interface NoteTypeService {
  Iterable<NoteType> findAll();
  NoteType findById(Long id);
}
