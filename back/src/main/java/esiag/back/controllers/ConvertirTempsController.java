package esiag.back.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.services.ConvertirTempsService;

@RestController
public class ConvertirTempsController {

    private final ConvertirTempsService convertirTempsService;

    public ConvertirTempsController(ConvertirTempsService convertirTempsService) {
        this.convertirTempsService = convertirTempsService;
    }

    @PostMapping("/temps-trajet/ambulances/minutes")
    public String convertirTempsTrajet() {
        convertirTempsService.convertirTemps();
        return "La conversion du temps du trajet est effectuée.";
    }
}