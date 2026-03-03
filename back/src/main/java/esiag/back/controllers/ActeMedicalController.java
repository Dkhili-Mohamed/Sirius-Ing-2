package esiag.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.services.ActeMedicalService;

@RestController
@RequestMapping("api/acte-medical")
public class ActeMedicalController {

    @Autowired
    private ActeMedicalService acteMedicalService;

    @PostMapping("insert/{idTypeActeMedical}/{idParcours}/{ordre}")
    public ResponseEntity<Boolean> insertActeMedical(@PathVariable Long idTypeActeMedical,
            @PathVariable Long idParcours,
            @PathVariable int ordre) {
        
        boolean inserer = acteMedicalService.insertActeMedical(idTypeActeMedical, idParcours, ordre);
        
        if (!inserer) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
