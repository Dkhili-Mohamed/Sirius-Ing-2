package esiag.back.controllers;

import esiag.back.models.ambulance.PatientA;
import esiag.back.services.patientAservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patientA")
public class patientAcontroller {

    @Autowired
    private patientAservice patientAService;

    
    @GetMapping("/{id}")
    public ResponseEntity<PatientA> findById(@PathVariable Long id) {
        PatientA patientA = patientAService.findByIdPatientA(id);
        return new ResponseEntity<>(patientA, HttpStatus.OK);
    }

    
    @GetMapping("/all")
    public ResponseEntity<List<PatientA>> findAllPatientA() {
        return new ResponseEntity<>(patientAService.findAllPatientsA(), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deletePatientA(@PathVariable Long id) {
        boolean isRemoved = patientAService.deletePatientA(id);

        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
