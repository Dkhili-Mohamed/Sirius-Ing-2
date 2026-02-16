package esiag.back.services;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import esiag.back.models.ambulance.Ambulance;
import esiag.back.repositories.ambulancerepository;

@Service
public class CalculNoteTrajetService {

    private final ambulancerepository ambulancerepository;

    public CalculNoteTrajetService(ambulancerepository ambulancerepository) {
        this.ambulancerepository = ambulancerepository;
    }

    public void calculerNoteTrajet() {

        List<Ambulance> ambulances = ambulancerepository.findAll();

        if (ambulances.isEmpty()) {
            System.out.println("Aucune ambulance trouvée.");
            return;
        }

        // Trier par temps trajet croissant 
        ambulances.sort(Comparator.comparing(Ambulance::getTempstrajet));

        int noteMax = 10;

        for (int i = 0; i < ambulances.size(); i++) {

            Ambulance ambulance = ambulances.get(i);

            if (ambulance.getTempstrajet() == null) {
                System.out.println("Temps trajet non calculé pour ambulance "
                        + ambulance.getIdambulance());
                continue;
            }

            double note = noteMax - i;

            if (note < 0) {
                note = 0;
            }

            ambulance.setNotetrajet(note);
            ambulancerepository.save(ambulance);

            System.out.println("Ambulance "
                    + ambulance.getIdambulance()
                    + " note trajet = " + note);
        }
    }
}
