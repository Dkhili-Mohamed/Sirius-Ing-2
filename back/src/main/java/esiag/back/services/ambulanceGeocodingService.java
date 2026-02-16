package esiag.back.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import esiag.back.models.ambulance.Ambulance;
import esiag.back.repositories.ambulancerepository;

@DependsOn("mockService")
@Service
public class ambulanceGeocodingService {

    private final ambulancerepository ambulancerepository;
    private final GeocodingService geocodingService;

    public ambulanceGeocodingService(ambulancerepository ambulancerepository,
                                     GeocodingService geocodingService) {
        this.ambulancerepository = ambulancerepository;
        this.geocodingService = geocodingService;
    }

    @PostConstruct
    @Transactional
    public void geocodeAllAmbulances() {

        List<Ambulance> ambulances = ambulancerepository.findAll();

        for (Ambulance ambulance : ambulances) {

        

            try {

                if (ambulance.getAdresseambulance() != null &&
                        ambulance.getAmbulancelatitude() == null &&
                        ambulance.getAmbulancelongitude() == null) {

                    double[] coords = geocodingService.getCoordinates(
                            ambulance.getAdresseambulance());

                    if (coords != null) {
                        ambulance.setAmbulancelatitude(coords[0]);
                        ambulance.setAmbulancelongitude(coords[1]);
                        ambulancerepository.save(ambulance);
                    }
                }
                
                Thread.sleep(1100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
