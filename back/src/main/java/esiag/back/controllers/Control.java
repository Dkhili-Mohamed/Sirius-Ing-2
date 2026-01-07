package esiag.back.controllers;


import esiag.back.models.architecture.Espace;
import esiag.back.models.dto.ParcoursPatient;
import esiag.back.models.medical.ActeMedical;
import esiag.back.services.CheminService;
import esiag.back.services.ParcoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("test")
public class Control {

    @Autowired
    CheminService cheminService;
    @Autowired
    ParcoursService parcoursService;

    @GetMapping("/")
    public ResponseEntity<List<Espace>> findChemin() {

        return new ResponseEntity<>(cheminService.findChemin(1L, 5L), HttpStatus.OK);
    }

    @GetMapping("/{idPatient}")
    public ResponseEntity<List<ParcoursPatient>> findParcours(@PathVariable Long idPatient) {
        return new ResponseEntity<>(parcoursService.findParcoursPatientById(idPatient), HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<List<ActeMedical>>  findActeMedicals() {
        return new ResponseEntity<>(parcoursService.findAllParcoursPatient(), HttpStatus.OK);
    }

    @GetMapping("parcourspatient/{idPatient}")
    public ResponseEntity<List<ParcoursPatient>>  findActeMedicalById(@PathVariable Long idPatient) {
        return new ResponseEntity<>(parcoursService.findParcoursPatientById(idPatient), HttpStatus.OK);
    }
    }
