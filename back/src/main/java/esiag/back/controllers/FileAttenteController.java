package esiag.back.controllers;

import esiag.back.models.medical.FileAttente;
import esiag.back.services.FileAttenteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/file-attente")
@Slf4j
public class FileAttenteController {

    @Autowired
    private FileAttenteService fileAttenteService;


    @GetMapping
    public ResponseEntity<List<FileAttente>> getFileAttente() {
        log.info("Récupération de la file d'attente triée");
        List<FileAttente> fileAttente = fileAttenteService.getFileAttenteTriee();
        log.info("File d'attente récupérée avec succès. {} entrées trouvées", fileAttente.size());
        return ResponseEntity.ok(fileAttente);
    }




}
