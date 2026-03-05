package esiag.back.models.dto;

import esiag.back.models.medical.BoxMedicale;
import esiag.back.models.medical.StatutBox;
import esiag.back.services.fileattente.BoxMedicaleService;
import esiag.back.services.fileattente.PatientService;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class BoxMedicaleDTO {
    private Long idBoxMedicale;
    private String nomBox;
    private esiag.back.models.dto.PatientDTO patient;
    private StatutBox statutBox;
    private LocalDateTime heureEntree;
    private LocalTime tempsEstime;
    private LocalDateTime libreA;
    private int tempsRestant;


    public BoxMedicaleDTO(BoxMedicale boxMedicale, PatientService patientService, BoxMedicaleService boxMedicaleService) {
        this.idBoxMedicale = boxMedicale.getIdBoxMedicale();
        this.nomBox = boxMedicale.getNomBox();
        this.statutBox = boxMedicale.getStatutBox();
        this.patient = new PatientDTO(boxMedicale.getPatient(), patientService);
        this.heureEntree = boxMedicale.getHeureEntree();
        this.tempsEstime = boxMedicale.getTempsEstime();
        this.libreA = boxMedicale.getLibreA();
        this.tempsRestant = boxMedicale.getTempsRestant();

    }


}