package esiag.back.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.models.dto.PatientStatutParcours;
import esiag.back.services.ParcoursService;

@RestController
@RequestMapping("api/parcours")
public class ParcoursController {
    
    @Autowired
    private ParcoursService parcoursService;
        
    
    @GetMapping("patientstatutparcours")
    public ResponseEntity<List<PatientStatutParcours>> findAllPatientStatutParcours() {
        return new ResponseEntity<>(parcoursService.findAllPatientStatutParcours(), HttpStatus.OK);
    }

    
}
