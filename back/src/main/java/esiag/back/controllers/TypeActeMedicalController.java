package esiag.back.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.models.medical.TypeActeMedical;
import esiag.back.services.parcours.TypeActeMedicalService;

@RestController
@RequestMapping("api/type-acte-medical")
public class TypeActeMedicalController {


    @Autowired
    private TypeActeMedicalService typeActeMedicalService;


    @GetMapping("{idPatient}")
    public ResponseEntity<List<TypeActeMedical>> findTypeActeMedicalBySymptomePatient(@PathVariable Long idPatient) {
        return new ResponseEntity<>(typeActeMedicalService.findAllTypeActeMedicalBySymptomePatient(idPatient), HttpStatus.OK);
    }

}
