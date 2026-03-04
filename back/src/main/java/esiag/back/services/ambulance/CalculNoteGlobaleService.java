package esiag.back.services.ambulance;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import esiag.back.models.ambulance.Ambulance;
import esiag.back.repositories.ambulancerepository;

@DependsOn("calculNoteTrajetService")
@Service
public class CalculNoteGlobaleService {

    private final ambulancerepository ambulancerepository;

    public CalculNoteGlobaleService(ambulancerepository ambulancerepository) {
        this.ambulancerepository = ambulancerepository;
    }
    @PostConstruct
    public void calculerNoteGlobale() {

        List<Ambulance> ambulances = ambulancerepository.findAll();

        if (ambulances.isEmpty()) {
            System.out.println("Aucune ambulance trouvée.");
            return;
        }

        for (Ambulance ambulance : ambulances) {

            //    Si ambulance non disponible
            if (Boolean.FALSE.equals(ambulance.getDisponibiliteambulance())) {

                ambulance.setNoteglobale(0.0);
                ambulancerepository.save(ambulance);

                System.out.println("Ambulance "
                        + ambulance.getIdambulance()
                        + " non disponible alors note la globale = 0");
                continue;
            }

            // Vérification des valeurs null
            if (ambulance.getExperienceambulance() == null
                    || ambulance.getEquipementambulance() == null
                    || ambulance.getNotetrajet() == null) {

                System.out.println(" Ambulance " + ambulance.getIdambulance()+ " manque des informations ");

                continue;
            }

            
            double noteGlobale =
                    ambulance.getExperienceambulance()
                    + ambulance.getEquipementambulance()
                    + ambulance.getNotetrajet();

            ambulance.setNoteglobale(noteGlobale);
            ambulancerepository.save(ambulance);

            System.out.println("Ambulance "
                    + ambulance.getIdambulance()
                    + "  note globale = " + noteGlobale);
        }
    }
}
