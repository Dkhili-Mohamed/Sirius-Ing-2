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


    @GetMapping("/all")
    public ResponseEntity<List<Patient>> getFileAttente() {
        List<Patient> liste = fileAttenteService.getPatientsTriees();
        if (liste.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(liste, HttpStatus.OK);
    }


    @GetMapping("/next")
    public ResponseEntity<Patient> getNextPatient() {
        List<Patient> liste = fileAttenteService.getPatientsTriees();
        if (liste.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(liste.get(0), HttpStatus.OK);
    }
}
