package esiag.back.controllers;

import esiag.back.models.medical.Patient;
import esiag.back.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/{id}")
    public ResponseEntity<Patient> findById(@PathVariable Long id){

        return new ResponseEntity<>(patientService.findByIdpatient(id), HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<List<Patient>> findAllPatient(){
        return new ResponseEntity<>(patientService.findAllpatient(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteMapping(@PathVariable Long id){
        boolean isRemoved = patientService.deletePatient(id);
        if(!isRemoved){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(id, HttpStatus.OK);
    }
}