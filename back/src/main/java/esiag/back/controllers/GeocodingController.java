package esiag.back.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.services.GeocodingService;

@RestController
public class GeocodingController {

    private final GeocodingService geocodingService;

    public GeocodingController(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
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
}
