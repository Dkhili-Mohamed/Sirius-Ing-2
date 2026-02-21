package esiag.back.models.dto;

import esiag.back.models.medical.BoxMedicale;
import esiag.back.models.medical.FileAttente;
import esiag.back.services.PatientService;
import lombok.Data;

import java.util.Timer;

@Data
public class BoxMedicaleDTO {
    private Long idBoxMedicale;
    private esiag.back.models.dto.PatientDTO patient;
    private Timer tempsAttenteEstime;


    public BoxMedicaleDTO(BoxMedicale boxMedicale, PatientService patientService, FileAttente fileAttente) {
        this.idBoxMedicale = boxMedicale.getIdBoxMedicale();
        this.patient = new PatientDTO(fileAttente.getPatient(), patientService);

    }
}