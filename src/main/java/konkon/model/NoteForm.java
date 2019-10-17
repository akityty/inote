package konkon.model;

public class NoteForm {
  private Long id;
  private String title;
  private String content;
  private NoteType noteType;

  public NoteForm(Long id, String title, String content, NoteType noteType) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.noteType = noteType;
  }

  public NoteForm() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public NoteType getNoteType() {
    return noteType;
  }

  public void setNoteType(NoteType noteType) {
    this.noteType = noteType;
  }
}
