package esiag.back.models.dto;

import esiag.back.models.medical.FileAttente;
import esiag.back.services.PatientService;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FileAttenteDTO {
    private Long idFileAttente;
    private Integer rang;
    private LocalDateTime dateEntree;
    private esiag.back.models.dto.PatientDTO patient;


    public FileAttenteDTO(FileAttente fileAttente, PatientService patientService) {
        this.idFileAttente = fileAttente.getIdFileAttente();
        this.rang = fileAttente.getRang();
        this.dateEntree = fileAttente.getDateEntree();

        if (fileAttente.getPatient() != null) {
            this.patient = new esiag.back.models.dto.PatientDTO(fileAttente.getPatient(), patientService);
        }
    }
}