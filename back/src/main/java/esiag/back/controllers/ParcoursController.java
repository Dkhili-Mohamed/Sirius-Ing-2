package esiag.back.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.models.dto.ParcoursPatient;
import esiag.back.models.dto.PatientStatutParcours;
import esiag.back.models.medical.Parcours;
import esiag.back.services.ActeMedicalService;
import esiag.back.services.CheminService;
import esiag.back.services.ParcoursService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/parcours")
public class ParcoursController {
    
    @Autowired
    private ParcoursService parcoursService;
    @Autowired
    private ActeMedicalService acteMedicalService;
    @Autowired
    private CheminService cheminService;
        
    
    @GetMapping("all")
    public ResponseEntity<List<PatientStatutParcours>> findAllPatientStatutParcours() {
        return new ResponseEntity<>(parcoursService.findAllPatientStatutParcours(), HttpStatus.OK);
    }

    @GetMapping("/{idPatient}")
    public ResponseEntity<List<ParcoursPatient>>  findActeMedicalById(@PathVariable Long idPatient) {
        return new ResponseEntity<>(parcoursService.findParcoursPatientById(idPatient), HttpStatus.OK);
    }

    @PostMapping("commencer")
    public ResponseEntity<ParcoursPatient> postMethodName(@RequestBody ParcoursPatient parcoursPatient) {
        boolean isUpdated = acteMedicalService.updateStatutPremierActeMedical(parcoursPatient);
        if(!isUpdated){ 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(parcoursPatient, HttpStatus.OK);
        
    }

    @GetMapping("chemin/{idTypeActeMedical}/{idActeMedical}/{idDepart}")
    public ResponseEntity<List<esiag.back.models.architecture.Espace>> findChemin(
            @PathVariable Long idTypeActeMedical,
            @PathVariable Long idActeMedical,
            @PathVariable Long idDepart) {
        return new ResponseEntity<>(
                cheminService.nextActeMedical(
                        idTypeActeMedical,
                        idActeMedical,
                        idDepart),
                HttpStatus.OK);
    }
    

    
}
