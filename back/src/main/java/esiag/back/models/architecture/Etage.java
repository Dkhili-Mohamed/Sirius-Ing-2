package esiag.back.models.architecture;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "etage")
public class Etage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_etage")
    private Long idEtage;

    @Column(name = "numero_etage")
    private String numeroEtage;

    @Column(name = "id_batiment")
    private String idBatiment;

    public Etage() {}

    @Override
    public String toString() {
        return "Etage{" +
                "idEtage=" + idEtage +
                ", numeroEtage='" + numeroEtage + '\'' +
                ", idBatiment='" + idBatiment + '\'' +
                '}';
    }
}
