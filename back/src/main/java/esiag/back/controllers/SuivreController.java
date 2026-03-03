package esiag.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.models.medical.Suivre;
import esiag.back.services.SuivreService;

@RestController
@RequestMapping("api/suivre")
public class SuivreController {

    @Autowired
    private SuivreService suivreService;


    @PostMapping("insert")
    public ResponseEntity<Suivre> insertSuivre(@RequestBody Suivre suivre) {

        Suivre savedSuivre = suivreService.insertSuivre(suivre);
        
        if (savedSuivre != null) {
            return new ResponseEntity<>(savedSuivre, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
