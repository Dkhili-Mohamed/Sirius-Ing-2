package esiag.back.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.services.CalculNoteGlobaleService;

@RestController
public class NoteGlobaleController {

    private final CalculNoteGlobaleService calculNoteGlobaleService;

    public NoteGlobaleController(CalculNoteGlobaleService calculNoteGlobaleService) {
        this.calculNoteGlobaleService = calculNoteGlobaleService;
    }

    @PostMapping("/note-globale/ambulances")
    public String calculerNoteGlobale() {
        calculNoteGlobaleService.calculerNoteGlobale();
        return "Notes globales calculées et enregistrées";
    }
}
