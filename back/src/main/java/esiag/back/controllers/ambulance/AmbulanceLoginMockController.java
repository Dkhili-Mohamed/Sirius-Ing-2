package esiag.back.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.services.ambulance.AmbulanceLoginMockService;

@RestController
public class AmbulanceLoginMockController {
    private final AmbulanceLoginMockService ambulanceLoginMockService;
    public AmbulanceLoginMockController(AmbulanceLoginMockService ambulanceLoginMockService) {
        this.ambulanceLoginMockService = ambulanceLoginMockService;
    }

    @PostMapping("/mock/ambulancelogin")
    public String genererLogins() {
        ambulanceLoginMockService.genererAmbulanceLogin();
        return "Mock login généré";
    }
}
