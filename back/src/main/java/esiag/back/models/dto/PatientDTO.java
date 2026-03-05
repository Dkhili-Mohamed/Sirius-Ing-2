package esiag.back.models.dto;

import esiag.back.models.medical.Patient;
import esiag.back.models.medical.StatutPatient;
import esiag.back.services.fileattente.PatientService;
import esiag.back.services.fileattente.PatientService;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PatientDTO {
    private Long idPatient;
    private String nomPatient;
    private String prenomPatient;
    private Integer agePatient;
    private List<String> symptomes;
    private Integer scoreUrgence;
    private String niveauUrgence;
    private StatutPatient statutPatient;


    public PatientDTO(Patient patient, PatientService patientService) {
        this.idPatient = patient.getIdPatient();
        this.nomPatient = patient.getNomPatient();
        this.prenomPatient = patient.getPrenomPatient();
        this.agePatient = patient.getAgePatient();
        this.symptomes = patient.getSymptomes() != null ? 
            new ArrayList<>(patient.getSymptomes()) : null;

        if (patientService != null) {
            this.scoreUrgence = patientService.calculerScoreUrgence(patient);
            this.niveauUrgence = patientService.getNiveauUrgence(patient).toString();
        }

        this.statutPatient = patient.getStatutPatient();
    }
}
