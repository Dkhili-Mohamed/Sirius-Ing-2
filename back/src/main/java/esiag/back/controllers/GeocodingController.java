package esiag.back.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.services.GeocodingService;
import esiag.back.services.ambulanceGeocodingService;

@RestController
public class GeocodingController {

    private final GeocodingService geocodingService;
    private final ambulanceGeocodingService ambulanceGeocodingService;

    public GeocodingController(GeocodingService geocodingService,
                               ambulanceGeocodingService ambulanceGeocodingService) {
        this.geocodingService = geocodingService;
        this.ambulanceGeocodingService = ambulanceGeocodingService;
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

    
    @PostMapping("/ambulances/geocode")
    public String geocodeAllAmbulances() {
        ambulanceGeocodingService.geocodeAllAmbulances();
        return "Toutes les ambulances ont été géocodées avec succès";
    }
}
