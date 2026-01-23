package esiag.back.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.models.ambulance.Ambulance;
import esiag.back.services.ambulanceservice;

@CrossOrigin(origins = "http://172.31.250.7:3000")
@RestController
@RequestMapping("api/ambulance")
public class ambulancecontroller {

    @Autowired
    private ambulanceservice ambulanceservice;

    
    @GetMapping("/{id}")
    public ResponseEntity<Ambulance> findById(@PathVariable Long id) {
        Ambulance ambulance = ambulanceservice.findById(id);
        return new ResponseEntity<>(ambulance, HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Ambulance>> findAll() {
        return new ResponseEntity<>(ambulanceservice.findAll(), HttpStatus.OK);
    }

   
    @PostMapping("/save")
    public ResponseEntity<Ambulance> save(@RequestBody Ambulance ambulance) {
        Ambulance saved = ambulanceservice.save(ambulance);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        boolean isRemoved = ambulanceservice.delete(id);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

 
    @GetMapping("/last")
    public ResponseEntity<Ambulance> findLast() {
        return new ResponseEntity<>(ambulanceservice.findLastAmbulance(), HttpStatus.OK);
    }
}
