package esiag.back.controllers.fileattente;

import esiag.back.models.medical.FileAttente;
import esiag.back.services.fileattente.FileAttenteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/file-attente")
@Slf4j
public class FileAttenteController {

    @Autowired
    private FileAttenteService fileAttenteService;


    @GetMapping
    public ResponseEntity<List<FileAttente>> getFileAttente() {
        log.info("Récupération de la file d'attente triée");
        List<FileAttente> fileAttente = fileAttenteService.getFileAttenteTriee();
        log.info("File d'attente récupérée avec succès. {} entrées trouvées", fileAttente.size());
        return ResponseEntity.ok(fileAttente);
    }

    @GetMapping("/nombre-patients")
    public ResponseEntity<Long> getNombrePatients() {
        try {
            long nombrePatients = fileAttenteService.getNombrePatients();
            return ResponseEntity.ok(nombrePatients);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/nombre-patients/urgents")
    public ResponseEntity<Long> getNombrePatientsUrgents() {
        try {
            long nombrePatientsUrgents = fileAttenteService.getNombrePatientsUrgents();
            return ResponseEntity.ok(nombrePatientsUrgents);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/nombre-patients/intermediaire")
    public ResponseEntity<Long> getNombrePatientsIntermediaire() {
        try {
            long nombrePatientsIntermediaires = fileAttenteService.getNombrePatientsIntermediaires();
            return ResponseEntity.ok(nombrePatientsIntermediaires);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/nombre-patients/non-urgents")
    public ResponseEntity<Long> getNombrePatientsNonUrgents() {
        try {
            long nombrePatientsNonUrgents = fileAttenteService.getNombrePatientsNonUrgents();
            return ResponseEntity.ok(nombrePatientsNonUrgents);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




}
