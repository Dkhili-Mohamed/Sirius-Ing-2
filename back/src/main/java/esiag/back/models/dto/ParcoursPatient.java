package esiag.back.models.dto;

import esiag.back.models.medical.StatutActeMedical;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ParcoursPatient {

    private Long idActeMedical;
    private Long idParcours;
    private Long idTypeActeMedical;
    private Long idEspace;
    private Long idSalle;
    private int ordre;
    private String libelle;
    private StatutActeMedical statut;
    private String numeroEspace;

    // Constructeur manuel pour correspondre exactement à la requête JPQL
    public ParcoursPatient(Long idActeMedical, Long idParcours, Long idTypeActeMedical, Long idEspace,
                           Long idSalle, int ordre, String libelle,
                           StatutActeMedical statut, String numeroEspace) {
        this.idActeMedical = idActeMedical;
        this.idParcours = idParcours;
        this.idTypeActeMedical = idTypeActeMedical;
        this.idEspace = idEspace;
        this.idSalle = idSalle;
        this.ordre = ordre;
        this.libelle = libelle;
        this.statut = statut;
        this.numeroEspace = numeroEspace;
    }
}
