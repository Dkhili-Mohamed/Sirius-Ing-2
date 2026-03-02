package esiag.back.models.dto;

import esiag.back.models.medical.FileAttente;
import esiag.back.repositories.BoxMedicaleRepository;
import esiag.back.services.FileAttenteService;
import esiag.back.services.PatientService;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

@Data
public class FileAttenteDTO {


    private Long idFileAttente;
    private Integer rang;
    private LocalDateTime dateEntree;
    private esiag.back.models.dto.PatientDTO patient;
    private int tempsAttenteEstime;


    public FileAttenteDTO(FileAttente fileAttente, PatientService patientService, int tempsAttenteEstime) {
        this.idFileAttente = fileAttente.getIdFileAttente();
        this.rang = fileAttente.getRang();
        this.dateEntree = fileAttente.getDateEntree();
        this.patient = new PatientDTO(fileAttente.getPatient(), patientService);
        this.tempsAttenteEstime = tempsAttenteEstime;

        if (fileAttente.getPatient() != null) {
            this.patient = new esiag.back.models.dto.PatientDTO(fileAttente.getPatient(), patientService);
        }
    }
}