package esiag.back.models.medical;

import esiag.back.models.architecture.Espace;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "salle")
public class Salle extends Espace {

    @Id
    @Column(name = "id_salle")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSalle;

    @Column(name = "capacite")
    private int capacite;

    @Column(name = "type_salle")
    private String typeSalle;

    @Column(name = "etat_salle")
    @Enumerated(EnumType.STRING)
    private EtatSalle etatSalle;

    @Column(name = "id_espace")
    private Long idEspace;

    @Column(name = "place_disponible")
    private int placeDisponible;

    @Override
    public String toString() {
        return "Salle{" +
                "idSalle=" + idSalle +
                ", capacite=" + capacite +
                ", typeSalle='" + typeSalle + '\'' +
                ", etatSalle=" + etatSalle +
                ", idEspace=" + idEspace +
                ", placeDisponible=" + placeDisponible +
                '}';
    }
}
