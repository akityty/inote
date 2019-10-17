package konkon.repository;

import konkon.model.Note;
import konkon.model.NoteType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NoteRepository extends PagingAndSortingRepository<Note, Long> {
  Page<Note> findAllByTitleContaining(String title, Pageable pageable);
  @Query("select  n from Note  n order by  n.id desc ")
  Page<Note> sort(Pageable pageable);
  Page<Note> findAllByNoteTypeId(Long id, Pageable pageable);
}
