package esiag.back;

import esiag.back.models.medical.FileAttente;
import esiag.back.models.medical.Patient;
import esiag.back.services.FileAttenteService;
import esiag.back.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestFileAttente implements CommandLineRunner {

    @Autowired
    private FileAttenteService fileAttenteService;

    @Autowired
    private PatientService patientService;

    @Override
    public void run(String... args) {
        System.out.println("=== DÉBUT DU TEST FILE D'ATTENTE ===");

        System.out.println("\nListe de tous les patients :");
        for (Patient patient : patientService.findAllPatients()) {
            System.out.println("- [Arrivé(e) le : " + patient.getDateArrivee() + "] " + patient.getPrenomPatient() + " " +
                    patient.getNomPatient() + " " + patient.getAgePatient() + " ans" +
                    " (Score: " + patient.getScoreUrgence() + 
                    " - Niveau: " + patient.getNiveauUrgence() + ")");
        }

        System.out.println("\nFile d'attente triée par urgence :");
        for (FileAttente fileAttente : fileAttenteService.getFileAttenteTriee()) {
            Patient patient = fileAttente.getPatient();
            System.out.println("- [Arrivé(e) le : " + patient.getDateArrivee() + "] " + patient.getPrenomPatient() + " " +
                    patient.getNomPatient() + " " + patient.getAgePatient() + " ans" +
                    " (Score: " + patient.getScoreUrgence() + 
                    " - Niveau: " + patient.getNiveauUrgence() + ")");
        }

        System.out.println("=== FIN DU TEST ===");
    }
}