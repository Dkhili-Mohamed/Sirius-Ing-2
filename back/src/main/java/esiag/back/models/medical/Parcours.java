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

    @ManyToOne
    @JoinColumn(name = "id_medecin", nullable = false)
    private Medecin medecin;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_global")
    private StatutActeMedical statutGlobal;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "nom_parcours")
    private String nomParcours;

    @Override
    public String toString() {
        return "Parcours{" +
                "idParcours=" + idParcours +
                ", medecin=" + medecin +
                ", statutGlobal='" + statutGlobal + '\'' +
                ", dateCreation=" + dateCreation +
                ", nomParcours='" + nomParcours + '\'' +
                '}';
    }
}
