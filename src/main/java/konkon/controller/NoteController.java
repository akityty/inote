package konkon.controller;

import konkon.model.Note;
import konkon.model.NoteForm;
import konkon.model.NoteType;
import konkon.service.NoteService;
import konkon.service.NoteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class NoteController {
  @Autowired
  private NoteTypeService noteTypeService;
  @Autowired
  private NoteService noteService;

  @ModelAttribute("noteTypes")
  public Iterable<NoteType> getAllNoteTypes() {
    return noteTypeService.findAll();
  }

  @GetMapping("/note/list")
  public ModelAndView showListNote(@PageableDefault(value = 3) Pageable pageable, HttpServletRequest request) {
     Page<Note> notes = noteService.findAll(pageable);
    ModelAndView modelAndView = new ModelAndView("/note/list");
    modelAndView.addObject("notes", notes);
    if (request.getParameter("message")!= null){
      modelAndView.addObject("message",request.getParameter("message"));
    }
    return modelAndView;
  }

  @GetMapping("/note/create")
  public ModelAndView showCreateForm() {
    ModelAndView modelAndView = new ModelAndView("/note/create");
    modelAndView.addObject("noteForm", new NoteForm());
    return modelAndView;
  }

  @PostMapping("/note/create")
  public ModelAndView saveEmployee(@ModelAttribute NoteForm note) throws IOException {
    noteService.save(note);
    return new ModelAndView("redirect:/note/list");
  }

  @GetMapping("/note/edit/{id}")
  public ModelAndView showEditForm(@PathVariable Long id) {
    Note note = noteService.findById(id);
    if (note != null) {
      ModelAndView modelAndView = new ModelAndView("/note/edit");
      modelAndView.addObject("note", note);
      return modelAndView;
    } else {
      return new ModelAndView("/error");
    }
  }

  @PostMapping("/note/edit")
  public ModelAndView editNote(@ModelAttribute NoteForm noteForm) throws IOException {
    noteService.save(noteForm);
    return new ModelAndView("redirect:/note/list");
  }

  @GetMapping("/note/delete/{id}")
  public ModelAndView showDelete(@PathVariable Long id) {
    Note note = noteService.findById(id);
    if (note != null) {
      ModelAndView modelAndView = new ModelAndView("/note/delete");
      modelAndView.addObject("note", note);
      return modelAndView;
    } else {
      return new ModelAndView("/error");
    }
  }

  @PostMapping("/note/delete")
  public ModelAndView modelAndView(@ModelAttribute NoteForm noteForm) {
    noteService.delete(noteForm.getId());
    return new ModelAndView("redirect:/note/list");
  }

  @GetMapping("/note/view/{id}")
  public ModelAndView showViewForm(@PathVariable Long id) {
    Note note = noteService.findById(id);
    if (note != null) {
      ModelAndView modelAndView = new ModelAndView("/note/view");
      modelAndView.addObject("note", note);
      return modelAndView;
    } else {
      return new ModelAndView("/error");
    }
  }

  @GetMapping("/note/noteType")
  public ModelAndView noteType( @RequestParam("id") Long id, @PageableDefault(value = 3) Pageable pageable){
    ModelAndView modelAndView = new ModelAndView("note/noteType");
    Page<Note> notes = noteService.findAllByNoteTypeId(id, pageable);
    modelAndView.addObject("id",id );
    modelAndView.addObject("notes", notes);
    return modelAndView;
  }

  @GetMapping("/note/searchByNoteTypeId")
  public ModelAndView showNotesByNoteType(@RequestParam("id") Long id) {
    ModelAndView modelAndView = new ModelAndView("redirect:/note/noteType");
    modelAndView.addObject("id", id);
    return modelAndView;
  }


  @GetMapping("/note/title")
  public ModelAndView title( @RequestParam("title") String title, @PageableDefault(value = 3) Pageable pageable){
    ModelAndView modelAndView = new ModelAndView("note/title");
    Page<Note> notes = noteService.findAllByTitleContaining(title, pageable);
    modelAndView.addObject("title",title  );
    modelAndView.addObject("notes", notes);
    return modelAndView;
  }

  @GetMapping("note/searchByTitle")
  public ModelAndView showNotesByTitle(@RequestParam("title") String title) {
    ModelAndView modelAndView = new ModelAndView("redirect:/note/title");
    modelAndView.addObject("title", title);
    return modelAndView;
  }
  @GetMapping("/note/writeJSON")
  public ModelAndView writeJSON(){

    noteService.writeJSON();
    ModelAndView modelAndView = new ModelAndView("redirect:/note/list");
    modelAndView.addObject("message","Export successful");
    return modelAndView;
  }

  @GetMapping("/note/importJSON")
  public ModelAndView importJSON(){
    noteService.importJSON();
    ModelAndView modelAndView = new ModelAndView("redirect:/note/list");
    modelAndView.addObject("message","Import successful");
    return modelAndView;
  }

}
