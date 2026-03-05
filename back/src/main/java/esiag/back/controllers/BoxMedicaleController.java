package esiag.back.controllers;

import esiag.back.models.medical.BoxMedicale;
import esiag.back.services.BoxMedicaleService;
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







}

