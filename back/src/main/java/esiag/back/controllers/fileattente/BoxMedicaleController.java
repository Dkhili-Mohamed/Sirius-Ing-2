package esiag.back.controllers.fileattente;

import esiag.back.models.medical.BoxMedicale;
import esiag.back.services.fileattente.BoxMedicaleService;
import esiag.back.services.fileattente.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("api/box-medicale")
public class BoxMedicaleController {

    @Autowired
    private BoxMedicaleService boxMedicaleService;
    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<List<BoxMedicale>> getAllBoxMedicale() {
        try{

            if(boxMedicaleService.getAllBoxMedicales() == null) {
                return ResponseEntity.badRequest().build();
            }

            List<BoxMedicale> boxMedicales = boxMedicaleService.getAllBoxMedicales();
            return ResponseEntity.ok(boxMedicales);


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping ("liberer/{idBoxMedicale}/{idPatient}")//pour supprimer un patient de la box, avec un appel a liberer box
    public ResponseEntity<Long> libererPatientBox(@PathVariable Long idBoxMedicale, @PathVariable Long idPatient) {
        try{
            boxMedicaleService.libererBox(idBoxMedicale);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }







}

