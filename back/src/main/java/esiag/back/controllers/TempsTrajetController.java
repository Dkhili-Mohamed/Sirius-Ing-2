package esiag.back.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.services.CalculTempsTrajetService;

@RestController
public class TempsTrajetController {

    private final CalculTempsTrajetService calculTempsTrajetService;

    public TempsTrajetController(CalculTempsTrajetService calculTempsTrajetService) {
        this.calculTempsTrajetService = calculTempsTrajetService;
    }

    @PostMapping("/temps-trajet/ambulances")
    public String calculerTempsTrajet() {
        calculTempsTrajetService.calculerTempsTrajet();
        return "Temps de trajet calculés et enregistrés";
    }
}
