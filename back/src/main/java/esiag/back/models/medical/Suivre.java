package esiag.back.models.medical;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "suivre")
public class Suivre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_suivre")
    private Long idSuivre;

    @ManyToOne
    @JoinColumn(name = "id_parcours", nullable = false)
    private Parcours parcours;

    @ManyToOne
    @JoinColumn(name = "id_patient", nullable = false)
    private Patient patient;

    @Override
    public String toString() {
        return "Suivre{" +
                "idSuivre=" + idSuivre +
                ", parcours=" + parcours +
                ", patient=" + patient +
                '}';
    }
}
