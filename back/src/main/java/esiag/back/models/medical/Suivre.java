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

    @Column(name = "id_patient")
    private Long idPatient;

    @Column(name = "id_parcours")
    private Long idParcours;

    @Override
    public String toString() {
        return "Suivre{" +
                "idSuivre=" + idSuivre +
                ", idPatient=" + idPatient +
                ", idParcours=" + idParcours +
                '}';
    }
}
