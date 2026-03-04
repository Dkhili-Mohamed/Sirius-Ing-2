package esiag.back.services.ambulance;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import esiag.back.models.ambulance.Ambulance;
import esiag.back.repositories.ambulance.ambulancerepository;

@DependsOn("calculNoteGlobaleService")
@Service
public class ConvertirTempsService {

    private final ambulancerepository ambulancerepository;

    public ConvertirTempsService(ambulancerepository ambulancerepository) {
        this.ambulancerepository = ambulancerepository;
    }
   
    @PostConstruct
    public void convertirTemps() {

        List<Ambulance> ambulances = ambulancerepository.findAll();

        if (ambulances.isEmpty()) {
            System.out.println("aucune ambulance trouvée.");
            return;
        }

        for (Ambulance ambulance : ambulances) {
            Double temps = ambulance.getTempstrajet();

            if (temps == null) {
                System.out.println("Le temps du trajet n'est pas calculé pour l'ambulance "
                        + ambulance.getIdambulance());
                continue;
            }
            int heures = temps.intValue(); 
            int minutes = (int) ((temps - heures) * 60);
            String t = heures + "H" + minutes + "Min";

            ambulance.setTempstrajetminutes(t);
            ambulancerepository.save(ambulance);

            System.out.println("Ambulance " + ambulance.getIdambulance() + t);
        }
    }
}
