package konkon.service;

import konkon.model.Note;
import konkon.model.NoteForm;
import konkon.model.NoteType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteService {
  /* void delete(Long id);
    Employee getEmployee(EmployeeForm employeeForm);
    Employee findById(Long id);*/

  void delete(Long id);
  void save(NoteForm noteForm);
  Note getNote(NoteForm noteForm);

  Note findById(Long id);

  Page<Note> findAll(Pageable pageable);
  Page<Note> findAllByNoteTypeId(Long id, Pageable pageable);
  Page<Note> findAllByTitleContaining(String title, Pageable pageable);
  /* Page<Employee> findAllByNameContaining(String name,Pageable pageable);*/
}
