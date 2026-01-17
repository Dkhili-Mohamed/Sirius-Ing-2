package esiag.back.controllers;

import esiag.back.models.medical.DPI;
import esiag.back.services.DPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dpi")
public class DPIController {

    @Autowired
    private DPIService dpiService;

    @GetMapping("/{id}")
    public ResponseEntity<DPI> findById(@PathVariable Long id){

        return new ResponseEntity<>(dpiService.findByIdDPI(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DPI>> findAllDPI(){
        return new ResponseEntity<>(dpiService.findAllDPIs(), HttpStatus.OK);
    }

    @GetMapping("/patient/{idPatient}")
    public List<DPI> getDpiByPatient(@PathVariable Long idPatient) {
        return dpiService.findByPatientId(idPatient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DPI> updateDPI(@PathVariable Long id, @RequestBody DPI dpiUpdates) {

        DPI dpiExistant = dpiService.findByIdDPI(id);

        if(dpiExistant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        dpiExistant.setAntecedent(dpiUpdates.getAntecedent());
        dpiExistant.setTraitement(dpiUpdates.getTraitement());

        dpiService.saveDPI(dpiExistant);

        return  new ResponseEntity<>(dpiExistant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteMapping(@PathVariable Long id){
        boolean isRemoved = dpiService.deleteDPI(id);
        if(!isRemoved){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(id, HttpStatus.OK);
    }



}