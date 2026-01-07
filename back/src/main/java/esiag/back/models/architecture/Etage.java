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

    @ManyToMany
    @JoinColumn(name = "id_batiment", nullable = false)
    private Batiment batiment;

    public Etage() {}

    @Override
    public String toString() {
        return "Etage{" +
                "idEtage=" + idEtage +
                ", numeroEtage='" + numeroEtage + '\'' +
                ", batiment='" + batiment + '\'' +
                '}';
    }
}
