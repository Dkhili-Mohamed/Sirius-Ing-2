package esiag.back.models.dto;

import esiag.back.models.medical.StatutActeMedical;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatientStatutParcours{
    
    private Long idPatient;
    private Long idParcours;
    private String nomParcours;
    private String nomPatient;
    private String prenomPatient;
    private StatutActeMedical statutParcours;

    public PatientStatutParcours(Long idParcours, Long idPatient, String nomParcours, String nomPatient, String prenomPatient, StatutActeMedical statutParcours) {
        this.idPatient = idPatient;
        this.idParcours = idParcours;
        this.nomPatient = nomPatient;
        this.prenomPatient = prenomPatient;
        this.statutParcours = statutParcours;
    }   




    
}
