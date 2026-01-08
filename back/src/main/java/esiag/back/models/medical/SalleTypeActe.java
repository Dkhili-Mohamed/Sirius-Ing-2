package esiag.back.models.medical;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "salle_type_acte")
public class SalleTypeActe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_salle_type_acte")
    private Long idSalleTypeActe;

    @Column(name = "id_salle")
    private Long idSalle;

    @Column(name = "id_type_acte_medical")
    private Long idTypeActeMedical;

    @Override
    public String toString() {
        return "SalleTypeActe{" +
                "idSalleTypeActe=" + idSalleTypeActe +
                ", idSalle=" + idSalle +
                ", idTypeActeMedical=" + idTypeActeMedical +
                '}';
    }
}
