package esiag.back.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.services.GeocodingService;
import esiag.back.services.ambulanceGeocodingService;
import esiag.back.services.patientAGeocodingService;

@RestController
public class GeocodingController {

    private final GeocodingService geocodingService;
    private final ambulanceGeocodingService ambulanceGeocodingService;
    private final patientAGeocodingService patientAGeocodingService;

    public GeocodingController(GeocodingService geocodingService,
                               patientAGeocodingService patientAGeocodingService,
                               ambulanceGeocodingService ambulanceGeocodingService) {
        this.geocodingService = geocodingService;
        this.ambulanceGeocodingService = ambulanceGeocodingService;
        this.patientAGeocodingService = patientAGeocodingService;
    }

    
    @GetMapping("/geocode")
    public String geocode(@RequestParam String adresse) {
        double[] coords = geocodingService.getCoordinates(adresse);
        if (coords != null) {
            return "Latitude: " + coords[0] + ", Longitude: " + coords[1];
        } else {
            return "Adresse introuvable";
        }
    }
    @PostMapping("/patientsA/geocode")
    public String geocodeAllPatientsA() {
        patientAGeocodingService.geocodeAllPatientsA(); 
        return "Tous les patients ont été géocodés avec succès";
    }


    
    @PostMapping("/ambulances/geocode")
    public String geocodeAllAmbulances() {
        ambulanceGeocodingService.geocodeAllAmbulances();
        return "Toutes les ambulances ont été géocodées avec succès";
    }
}
