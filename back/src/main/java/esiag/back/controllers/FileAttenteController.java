package esiag.back.controllers;

import esiag.back.models.medical.Patient;
import esiag.back.services.FileAttenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/file-attente")
public class FileAttenteController {

    @Autowired
    private FileAttenteService fileAttenteService;


    @PostMapping("/ajouter/malades")
    public ResponseEntity<String> ajouterPatientsMalades() {
        fileAttenteService.ajouterPatientsMalades();
        return ResponseEntity.ok("Traitement des patients malades effectué");
    }

}
