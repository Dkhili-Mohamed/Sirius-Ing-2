package esiag.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import esiag.back.models.ambulance.PatientA;
import esiag.back.services.patientAservice;

import java.util.List;

@SpringBootApplication
public class EsiagBackApplication {

    public static void main(String[] args) {
        
        ApplicationContext context = SpringApplication.run(EsiagBackApplication.class, args);

        
        patientAservice patientAService = context.getBean(patientAservice.class);

        
        List<PatientA> patients = patientAService.findAllPatientsA();

        
        if (patients.isEmpty()) {
            System.out.println("Aucun patient trouvé dans la table patientA.");
        } else {
            System.out.println("Liste des patientsA :");
            for (PatientA p : patients) {
                System.out.println("ID : " + p.getIdpatientA());
                System.out.println("Nom : " + p.getNompatientA());
                System.out.println("Adresse : " + p.getAdressepatientA());
                System.out.println("Numéro : " + p.getNumeropatientA());
                System.out.println("----------------------------------");
            }
        }
    }
}