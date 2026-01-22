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

        System.out.println("1. Patients non triés :");
        for (Patient patient : patientService.findAllPatients()) {
            System.out.println("- " + patient.getPrenomPatient() + " " + patient.getNomPatient());
        }

        System.out.println("2. File d'attente triée par urgence :");
        for (FileAttente fileAttente : fileAttenteService.getFileAttenteTriee()) {
            Patient patient = fileAttente.getPatient();
            System.out.println("- [Position " + fileAttente.getRang() + "] " +
                    patient.getPrenomPatient() + " " + patient.getNomPatient() +
                    " (Score: " + patient.getScoreUrgence() +
                    " - Niveau: " + patient.getNiveauUrgence() + ")");
        }

        System.out.println("=== FIN DU TEST ===");
    }
}