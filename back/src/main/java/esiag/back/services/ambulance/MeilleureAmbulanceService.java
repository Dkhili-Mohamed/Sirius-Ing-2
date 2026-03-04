package esiag.back.services.ambulance;

import java.util.List;

import org.springframework.stereotype.Service;

import esiag.back.models.ambulance.Ambulance;
import esiag.back.repositories.ambulance.ambulancerepository;

@Service
public class MeilleureAmbulanceService {

    private final ambulancerepository ambulancerepository;

    public MeilleureAmbulanceService(ambulancerepository ambulancerepository) {
        this.ambulancerepository = ambulancerepository;
    }



    public Long MeilleureAmbulance() {
        List<Ambulance> ambulances = ambulancerepository.findAll();
        double max = 0;   
        Long idMeilleure = null;

        for (Ambulance a : ambulances) {
            if (a.getNoteglobale() != null && a.getNoteglobale() > max) {
                max = a.getNoteglobale();
                idMeilleure = a.getIdambulance();
            }
        }

        return idMeilleure;
    }
}