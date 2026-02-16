package esiag.back.services;

import java.util.List;

import org.springframework.stereotype.Service;

import esiag.back.models.ambulance.Ambulance;
import esiag.back.repositories.ambulancerepository;

@Service
public class CalculTempsTrajetService {

    private final ambulancerepository ambulancerepository;

    public CalculTempsTrajetService(ambulancerepository ambulancerepository) {
        this.ambulancerepository = ambulancerepository;
    }

    public void calculerTempsTrajet() {

        List<Ambulance> ambulances = ambulancerepository.findAll();

        if (ambulances.isEmpty()) {
            System.out.println("Aucune ambulance trouvée.");
            return;
        }

        for (Ambulance ambulance : ambulances) {

            if (ambulance.getAmbulancedistance() == null) {
                System.out.println("Distance non calculée pour ambulance "
                        + ambulance.getIdambulance());
                continue;
            }

            if (ambulance.getVitessemoyambulance() == null 
                || ambulance.getVitessemoyambulance() == 0) {

                System.out.println("Vitesse invalide pour ambulance "
                        + ambulance.getIdambulance());
                continue;
            }

            // Temps = Distance / Vitesse
            double temps = ambulance.getAmbulancedistance()
                    / ambulance.getVitessemoyambulance();

            ambulance.setTempstrajet(temps);

            ambulancerepository.save(ambulance);

            System.out.println("Temps trajet ambulance "
                    + ambulance.getIdambulance()
                    + " = " + temps + " heures");
        }
    }
}
