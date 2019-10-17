package konkon.repository;

import konkon.model.NoteType;
import org.springframework.data.repository.CrudRepository;

public interface TypeNoteRepository extends CrudRepository<NoteType,Long > {
}
