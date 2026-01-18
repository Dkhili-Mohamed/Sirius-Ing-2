package esiag.back.controllers;

import esiag.back.models.medical.MaladiePatient;
import esiag.back.services.MaladiePatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/maladie-patient")
public class MaladiePatientController {

    @Autowired
    private MaladiePatientService maladiePatientService;

    @GetMapping("/{id}")
    public ResponseEntity<MaladiePatient> findById(@PathVariable Long id){

        return new ResponseEntity<>(maladiePatientService.findByIdMaladiePatient(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MaladiePatient>> findAllMaladiePatient(){
        return new ResponseEntity<>(maladiePatientService.findAllMaladiePatients(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaladiePatient> updateMaladiePatient(@PathVariable Long id, @RequestBody MaladiePatient maladiePatientUpdates) {
        MaladiePatient maladiePatientExistante = maladiePatientService.findByIdMaladiePatient(id);

        if(maladiePatientExistante == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        maladiePatientExistante.setNiveauCCMU(maladiePatientUpdates.getNiveauCCMU());

        MaladiePatient updated = maladiePatientService.updateMaladie(maladiePatientExistante);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping("/patient/{idPatient}")
    public ResponseEntity<List<MaladiePatient>> getMaladiesByPatient(@PathVariable Long idPatient) {
        List<MaladiePatient> maladies = maladiePatientService.findByPatientId(idPatient);
        return new ResponseEntity<>(maladies, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteMapping(@PathVariable Long id){
        boolean isRemoved = maladiePatientService.deleteMaladiePatient(id);
        if(!isRemoved){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping("/ajouter")
    public ResponseEntity<MaladiePatient> ajouterMaladiePatient(@RequestBody MaladiePatient maladiePatient) {
        MaladiePatient resultat = maladiePatientService.ajouterMaladie(maladiePatient);
        return new ResponseEntity<>(resultat, HttpStatus.OK);
    }
}