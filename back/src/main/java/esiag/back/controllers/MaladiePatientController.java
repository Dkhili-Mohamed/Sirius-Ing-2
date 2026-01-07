package esiag.back.controllers;

import esiag.back.models.medical.MaladiePatient;
import esiag.back.models.medical.MaladiePatient;
import esiag.back.services.MaladiePatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/MaladiePatient")
public class MaladiePatientController {

    @Autowired
    private MaladiePatientService maladiePatientService;

    @GetMapping("/{id}")
    public ResponseEntity<MaladiePatient> findById(@PathVariable Long id){

        return new ResponseEntity<>(maladiePatientService.findByIdMaladiePatient(id), HttpStatus.OK);
    }

    @GetMapping("all")
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

        maladiePatientService.saveMaladiePatient(maladiePatientExistante);

        return  new ResponseEntity<>(maladiePatientExistante, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteMapping(@PathVariable Long id){
        boolean isRemoved = maladiePatientService.deleteMaladiePatient(id);
        if(!isRemoved){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(id, HttpStatus.OK);
    }
}