package esiag.back.services;

import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import esiag.back.models.ambulance.Ambulance;
import esiag.back.repositories.ambulancerepository;

@DependsOn("calculTempsTrajetService")
@Service
public class CalculNoteTrajetService {

    private final ambulancerepository ambulancerepository;

    public CalculNoteTrajetService(ambulancerepository ambulancerepository) {
        this.ambulancerepository = ambulancerepository;
    }
    @PostConstruct
    public void calculerNoteTrajet() {

        List<Ambulance> ambulances = ambulancerepository.findAll();

        if (ambulances.isEmpty()) {
            System.out.println("Aucune ambulance trouvée.");
            return;
        }

        // Trier par temps de trajet 
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
            }  // Je voudrais noter que les 10 ambulances qui ont les temps de trajet les plus courts et le reste je lui donne 0 

            ambulance.setNotetrajet(note);
            ambulancerepository.save(ambulance);

            System.out.println("Ambulance "+ ambulance.getIdambulance()+ " note trajet = " + note);
        }
    }
}
