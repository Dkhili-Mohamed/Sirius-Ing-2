package esiag.back.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.models.ambulance.Intervention;
import esiag.back.repositories.InterventionRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/interventions")
public class InterventionController {

    @Autowired
    private InterventionRepository repository;

    
    @PostMapping
    public Intervention create(@RequestBody Intervention intervention) {
        return repository.save(intervention);
    }

    
    @GetMapping
    public List<Intervention> getAll() {
        return repository.findAll();
    }

    
    @GetMapping("/recentes")
    public List<Intervention> getRecentes() {
        return repository.findAllByOrderByDateinterventionDesc();
    }


    @PutMapping("/{id}")
    public Intervention update(@PathVariable Long id, @RequestBody Intervention updated) {
        return repository.findById(id).map(intervention -> {
            intervention.setNomintervention(updated.getNomintervention());
            intervention.setAdresseintervention(updated.getAdresseintervention());
            intervention.setInterventionstatut(updated.getInterventionstatut());
            return repository.save(intervention);
        }).orElse(null);
    }
}