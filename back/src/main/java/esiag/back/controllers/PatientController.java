package esiag.back.controllers;

import esiag.back.models.dto.FileAttenteDTO;
import esiag.back.models.dto.ParcoursPatient;
import esiag.back.models.medical.Patient;
import esiag.back.services.PatientService;
import esiag.back.models.medical.FileAttente;
import esiag.back.services.FileAttenteService;
import esiag.back.services.ParcoursService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private ParcoursService parcoursService;

    @Autowired
    private FileAttenteService fileAttenteService;

    @GetMapping("/{id}")
    public ResponseEntity<Patient> findById(@PathVariable Long id){

        return new ResponseEntity<>(patientService.findByIdPatient(id), HttpStatus.OK);
    }


    @GetMapping("all")
    public ResponseEntity<List<Patient>> findAllPatient(){
        return new ResponseEntity<>(patientService.findAllPatients(), HttpStatus.OK);
    }

    //Sera peut-être utilisé ultérieurement
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Long> deleteMapping(@PathVariable Long id){
//        boolean isRemoved = patientService.deletePatient(id);
//        if(!isRemoved){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return  new ResponseEntity<>(id, HttpStatus.OK);
//    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient) {
        try {
            Patient savedPatient = patientService.save(patient);
            return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("file-attente")
    public ResponseEntity<List<FileAttente>> getFileAttente(){
        try {
            List<FileAttente> fileAttente = fileAttenteService.getFileAttenteTriee();
            return new ResponseEntity<>(fileAttente, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("file-attente-dto")
    public ResponseEntity<List<FileAttenteDTO>> getFileAttenteDTO() {
        List<FileAttenteDTO> fileAttente = fileAttenteService.getFileAttenteAvecScores();
        return ResponseEntity.ok(fileAttente);
    }

    @GetMapping("/{email}/parcours")
    public ResponseEntity<List<ParcoursPatient>> getParcoursPatientByEmail(@PathVariable String email) {
        List<ParcoursPatient> parcoursPatients = patientService.getParcoursPatientsByEmail(email);
        if (parcoursPatients == null || parcoursPatients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(parcoursPatients, HttpStatus.OK);
    }

    @GetMapping("/{id}/parcours/historique")
    public ResponseEntity<List<ParcoursPatient>> getHistoriqueParcoursPatients(@PathVariable Long id) {
        List<ParcoursPatient> parcoursPatients = parcoursService.findHistoriqueParcoursPatients(id);
        if (parcoursPatients == null || parcoursPatients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(parcoursPatients, HttpStatus.OK);   
    }

}