package esiag.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.models.medical.ActeMedical;
import esiag.back.services.parcours.ActeMedicalService;

@RestController
@RequestMapping("api/acte-medical")
public class ActeMedicalController {

    @Autowired
    private ActeMedicalService acteMedicalService;

    @PostMapping("insert")
    public ResponseEntity<ActeMedical> insertActeMedical(@RequestBody ActeMedical acteMedical) {
        
        ActeMedical inserer = acteMedicalService.insertActeMedical(acteMedical);
        
        if (inserer != null) {
            
            return new ResponseEntity<>(inserer, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
