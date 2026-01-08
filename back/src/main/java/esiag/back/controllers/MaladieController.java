package esiag.back.controllers;

import esiag.back.models.medical.DPI;
import esiag.back.models.medical.Maladie;
import esiag.back.services.MaladieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/Maladie")
public class MaladieController {

    @Autowired
    private MaladieService maladieService;

    @GetMapping("/{id}")
    public ResponseEntity<Maladie> findById(@PathVariable Long id){

        return new ResponseEntity<>(maladieService.findByIdMaladie(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Maladie>> findAllMaladie(){
        return new ResponseEntity<>(maladieService.findAllMaladies(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Maladie> updateMaladie(@PathVariable Long id, @RequestBody Maladie maladieUpdates) {

        Maladie maladieExistante = maladieService.findByIdMaladie(id);

        if(maladieExistante == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        maladieExistante.setNomMaladie(maladieExistante.getNomMaladie());
        maladieExistante.setDescriptionMaladie(maladieExistante.getDescriptionMaladie());
        maladieExistante.setDateDiagnostic(maladieExistante.getDateDiagnostic());


        maladieService.saveMaladie(maladieExistante);

        return  new ResponseEntity<>(maladieExistante, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteMapping(@PathVariable Long id){
        boolean isRemoved = maladieService.deleteMaladie(id);
        if(!isRemoved){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(id, HttpStatus.OK);
    }
}