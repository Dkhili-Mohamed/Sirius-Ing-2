package esiag.back.controllers;

import esiag.back.models.dto.FileAttenteDTO;
import esiag.back.models.medical.Patient;
import esiag.back.repositories.FileAttenteRepository;
import esiag.back.repositories.PatientRepository;
import esiag.back.services.PatientService;
import esiag.back.models.medical.FileAttente;
import esiag.back.services.FileAttenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private FileAttenteService fileAttenteService;

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private FileAttenteRepository fileAttenteRepository;

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

            FileAttente fileAttente = new FileAttente();
            fileAttente.setPatient(savedPatient);
            fileAttente.setDateEntree(LocalDateTime.now());
            fileAttente.setRang((int) fileAttenteRepository.count() +1);
            fileAttenteRepository.save(fileAttente);

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
//        List<FileAttenteDTO> fileAttente = fileAttenteService.getFileAttenteAvecScores();
        List<FileAttenteDTO> fileAttente = fileAttenteService.calculerTempsAttenteEstime();
        return ResponseEntity.ok(fileAttente);
    }

}