package konkon.controller;

import konkon.model.NoteType;
import konkon.service.NoteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TypeNoteController {

  @Autowired
  private NoteTypeService noteTypeService;

  @GetMapping("/noteType/list")
  public ModelAndView departmentList() {
    Iterable<NoteType> noteTypes = noteTypeService.findAll();
    ModelAndView modelAndView = new ModelAndView("/noteType/list");
    modelAndView.addObject("noteTypes", noteTypes);
    return modelAndView;
  }
}
