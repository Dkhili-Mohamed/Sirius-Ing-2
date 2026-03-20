package esiag.back.controllers.parcours;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.models.dto.Chemin;
import esiag.back.models.dto.ParcoursPatient;
import esiag.back.models.dto.PatientStatutParcours;
import esiag.back.models.medical.Parcours;
import esiag.back.services.parcours.ActeMedicalService;
import esiag.back.services.parcours.CheminService;
import esiag.back.services.parcours.ParcoursService;

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

    @PostMapping("chemin/{idParcours}/{ordre}/{idDepart}")
    public ResponseEntity<List<Chemin>> findChemin(
            @PathVariable Long idParcours,
            @PathVariable int ordre,
            @PathVariable Long idDepart) {
        return new ResponseEntity<>(
                cheminService.nextActeMedical(
                        idParcours,
                        ordre,
                        idDepart),
                HttpStatus.OK);
    }

    @PostMapping("insert")
    public ResponseEntity<Parcours> insertParcours(@RequestBody Parcours parcours) {
        Parcours inserer = parcoursService.insertParcours(parcours);
        
        if (inserer != null) {

            return new ResponseEntity<>(inserer, HttpStatus.CREATED);
        } else {
        
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
