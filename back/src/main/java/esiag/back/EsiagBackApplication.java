package esiag.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import esiag.back.services.ambulanceservice;
import esiag.back.models.ambulance.Ambulance;

import java.util.List;

@SpringBootApplication
public class EsiagBackApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(EsiagBackApplication.class, args);

       
        ambulanceservice ambulanceservice = context.getBean(ambulanceservice.class);

        List<Ambulance> ambulances = ambulanceservice.findAll();

        
        System.out.println("LISTE DES AMBULANCES ");

        if (ambulances.isEmpty()) {
            System.out.println("Aucune ambulance trouvée.");
        } else {
            for (Ambulance a : ambulances) {
                System.out.println("ID : " + a.getIdambulance());
                System.out.println("Adresse : " + a.getAdresseambulance());
                System.out.println("Disponible : " + a.getDisponibiliteambulance());
                System.out.println("Vitesse moyenne : " + a.getVitessemoyambulance());
                System.out.println("Équipement : " + a.getEquipementambulance());
                System.out.println("Expérience : " + a.getExperienceambulance());
                System.out.println("Latitude : " + a.getAmbulancelatitude());
                System.out.println("Longitude : " + a.getAmbulancelongitude());
                System.out.println("-----------------------------------");
            }
        }
    }
}
