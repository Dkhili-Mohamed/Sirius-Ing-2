package esiag.back.models.architecture;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Data
@Table(name = "batiment")
public class Batiment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_batiment")
    private Long idBatiment;

    @Column(name = "nom")
    private String nomBatiment;

    public Batiment() {}

    @Override
    public String toString() {
        return "Batiment{" +
                "idBatiment=" + idBatiment +
                ", nomBatiment='" + nomBatiment + '\'' +
                '}';
    }
}
