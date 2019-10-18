package konkon.service.impl;

import konkon.model.Note;
import konkon.model.NoteForm;
import konkon.model.NoteType;
import konkon.repository.NoteRepository;
import konkon.repository.NoteTypeRepository;
import konkon.service.NoteService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.*;
import java.util.List;

public class NoteServiceImpl implements NoteService {
  @Autowired
  private NoteRepository noteRepository;
  @Autowired
  private NoteTypeRepository noteTypeRepository;


  @Override
  public void delete(Long id) {
    noteRepository.delete(id);
  }

  @Override
  public void save(NoteForm noteForm) throws IOException {
    Note note = getNote(noteForm);
    noteRepository.save(note);
  }

  @Override
  public Note getNote(NoteForm noteForm) {
    if (noteForm.getId() == null) {
      return new Note(noteForm.getTitle(), noteForm.getContent(), noteForm.getNoteType());
    } else {
      return new Note(noteForm.getId(), noteForm.getTitle(), noteForm.getContent(), noteForm.getNoteType());
    }
  }

  @Override
  public Note findById(Long id) {
    return noteRepository.findOne(id);
  }

  @Override
  public List<Note> findAll() {
    return (List<Note>) noteRepository.findAll();
  }

  @Override
  public Page<Note> findAll(Pageable pageable) {
    return noteRepository.sort(pageable);
  }

  @Override
  public Page<Note> findAllByNoteTypeId(Long id, Pageable pageable) {
    return noteRepository.findAllByNoteTypeId(id, pageable);
  }


  @Override
  public Page<Note> findAllByTitleContaining(String title, Pageable pageable) {
    return noteRepository.findAllByTitleContaining(title, pageable);
  }

  @Override
  public void saveNoteInFileTxt() {
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader("/home/konkon/Desktop/aa/Note Import.txt"));

      String textInALine = br.readLine();

      while ((textInALine = br.readLine()) != null) {
        System.out.println(textInALine);
        String[] noteString = textInALine.split(";");
        Note note = new Note();
        note.setTitle(noteString[0]);
        note.setContent(noteString[1]);
        long idNoteType = Long.parseLong(noteString[2]);
        note.setNoteType(noteTypeRepository.findOne(idNoteType));
        noteRepository.save(note);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        br.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void writeJSON() {
    JSONArray noteListJSON = new JSONArray();
    List<Note> notes = (List<Note>) noteRepository.findAll();
    for (int i = 0; i < notes.size(); i++) {

      JSONObject noteDetail = new JSONObject();

      JSONObject noteObject = new JSONObject();
      Note note = notes.get(i);
      noteDetail.put("id", note.getId());
      noteDetail.put("title", note.getTitle());
      noteDetail.put("content", note.getContent());
      noteDetail.put("noteTypeId", note.getNoteType().getId());

      noteObject.put("note", noteDetail);

      noteListJSON.add(noteObject);
    }
    //Write JSON file
    try (FileWriter file = new FileWriter("/home/konkon/Desktop/aa/ Export.json")) {

      file.write(noteListJSON.toJSONString());
      file.flush();

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void importJSON() {
    //JSON parser object to parse read file
    JSONParser jsonParser = new JSONParser();

    try (FileReader reader = new FileReader("/home/konkon/Desktop/aa/Import.json")) {
      //Read JSON file
      Object obj = jsonParser.parse(reader);

      JSONArray noteListJSON = (JSONArray) obj;
      System.out.println(noteListJSON);

      //Iterate over employee array
      for (int i = 0; i < noteListJSON.size(); i++) {
        JSONObject noteObject = (JSONObject) noteListJSON.get(i);
        Note note = parseNoteObject(noteObject);
        noteRepository.save(note);
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Note parseNoteObject(JSONObject note) {
    JSONObject noteObject = (JSONObject) note.get("note");

    String title = (String) noteObject.get("title");

    String content = (String) noteObject.get("content");

    Long noteTypeId = (Long) noteObject.get("noteTypeId");
    return new Note(title, content, noteTypeRepository.findOne(noteTypeId));

  }
}
