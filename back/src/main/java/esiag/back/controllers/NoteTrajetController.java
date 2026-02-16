package esiag.back.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.services.CalculNoteTrajetService;

@RestController
public class NoteTrajetController {

    private final CalculNoteTrajetService calculNoteTrajetService;

    public NoteTrajetController(CalculNoteTrajetService calculNoteTrajetService) {
        this.calculNoteTrajetService = calculNoteTrajetService;
    }

    @PostMapping("/note-trajet/ambulances")
    public String calculerNoteTrajet() {
        calculNoteTrajetService.calculerNoteTrajet();
        return "Notes de trajet calculées et enregistrées";
    }
}
