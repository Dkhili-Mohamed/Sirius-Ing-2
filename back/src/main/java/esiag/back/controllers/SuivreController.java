package esiag.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.services.SuivreService;

@RestController
@RequestMapping("api/suivre")
public class SuivreController {

    @Autowired
    private SuivreService suivreService;


    @PostMapping("insert/{idPatient}/{idParcours}")
    public ResponseEntity<Boolean> insertSuivre(@PathVariable Long idPatient, @PathVariable Long idParcours) {

        boolean inserer = suivreService.insertSuivre(idPatient, idParcours);
        
        if (!inserer) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
