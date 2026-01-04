package esiag.back.models.medical;

import esiag.back.models.architecture.Espace;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "salle")
public class Salle{

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

    @Column(name = "place_disponible")
    private int placeDisponible;

    @ManyToOne
    @JoinColumn(name = "id_espace", nullable = false)
    private Espace espace;

    @Override
    public String toString() {
        return "Salle{" +
                "idSalle=" + idSalle +
                ", capacite=" + capacite +
                ", typeSalle='" + typeSalle + '\'' +
                ", etatSalle=" + etatSalle +
                ", placeDisponible=" + placeDisponible +
                ", espace=" + espace +
                '}';
    }
}
