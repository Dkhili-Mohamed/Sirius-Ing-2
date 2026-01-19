package esiag.back.models.medical;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "salle_type_acte_medical")
public class SalleTypeActeMedical {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_salle_type_acte_medical")
    private Long idSalleTypeActeMedical;

    @ManyToOne
    @JoinColumn(name = "id_salle", nullable = false, referencedColumnName = "id_salle")
    private Salle salle;

    @ManyToOne
    @JoinColumn(name = "id_type_acte_medical", referencedColumnName = "id_type_acte_medical", nullable = false)
    private TypeActeMedical typeActeMedical;

    @Override
    public String toString() {
        return "SalleTypeActeMedical{" +
                "idSalleTypeActeMedical=" + idSalleTypeActeMedical +
                ", salle=" + salle +
                ", typeActeMedical=" + typeActeMedical +
                '}';
    }

}

