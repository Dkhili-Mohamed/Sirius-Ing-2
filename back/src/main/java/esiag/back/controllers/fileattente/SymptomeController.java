package esiag.back.controllers.fileattente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.services.fileattente.SymptomeService;

@RestController
@RequestMapping("api/symptome")
public class SymptomeController {

    @Autowired
    private SymptomeService symptomeService;

    @GetMapping("/{idPatient}")
    public ResponseEntity<List<esiag.back.models.medical.Symptome>> findSymptomeByIdPatient(@PathVariable Long idPatient) {

    
        return new ResponseEntity<>(symptomeService.findSymptomeByIdPatient(idPatient), HttpStatus.OK);
    }
    

}
