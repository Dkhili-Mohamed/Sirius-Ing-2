package esiag.back.models.dto;

import esiag.back.models.medical.BoxMedicale;
import esiag.back.services.PatientService;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class BoxMedicaleDTO {
    private Long idBoxMedicale;
//    private int disponibilite;
    private esiag.back.models.dto.PatientDTO patient;
    private LocalDateTime heureEntree;
    private LocalTime tempsEstime;


    public BoxMedicaleDTO(BoxMedicale boxMedicale, PatientService patientService) {
        this.idBoxMedicale = boxMedicale.getIdBoxMedicale();
//        this.disponibilite = boxMedicale.getDisponibilite();
        this.patient = new PatientDTO(boxMedicale.getPatient(), patientService);
        this.heureEntree = boxMedicale.getHeureEntree();
        this.tempsEstime = boxMedicale.getTempsEstime();
    }

    public int tempsRestant() {
        if(heureEntree == null || tempsEstime == null) {
            return 0;
        }
        int dureeTotaleMinutes =  tempsEstime.getHour() * 60 + tempsEstime.getMinute();

        Duration tempsEcoule = Duration.between(heureEntree, LocalDateTime.now());
        int tempsEcouleMinutes = (int) tempsEcoule.toMinutes();


        int tempsRestant = dureeTotaleMinutes - tempsEcouleMinutes;

        return Math.max(0, tempsRestant);


    }

}