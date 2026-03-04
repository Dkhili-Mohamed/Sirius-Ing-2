package esiag.back.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.services.ambulance.MeilleureAmbulanceService;

@RestController
public class MeilleureAmbulanceController {
    private final MeilleureAmbulanceService service;

    public MeilleureAmbulanceController(MeilleureAmbulanceService service) {
        this.service = service;
    }
    
    @GetMapping("/api/ambulance/meilleure")
    public Long getIdMeilleureAmbulance() {
        return service.MeilleureAmbulance();
    }
}