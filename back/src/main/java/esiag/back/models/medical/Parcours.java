package esiag.back.models.medical;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "parcours")
public class Parcours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parcours")
    private Long idParcours;

    @Column(name = "id_medecin")
    private Long idMedecin;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_global")
    private StatutActeMedical statutGlobal;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Override
    public String toString() {
        return "Parcours{" +
                "idParcours=" + idParcours +
                ", idMedecin=" + idMedecin +
                ", statutGlobal='" + statutGlobal + '\'' +
                ", dateCreation=" + dateCreation +
                '}';
    }
}
