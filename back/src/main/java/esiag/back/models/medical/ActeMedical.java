package esiag.back.models.medical;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "actemedical")
public class ActeMedical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acte_medical")
    private Long idActeMedical;

    @Column(name = "id_type_acte_medical")
    private Long idTyeActeMedical;

    @Column(name = "ordre")
    private int ordre;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatutActeMedical statut;

    @Column(name = "id_parcours")
    private Long idParcours;

    @Column(name = "id_salle")
    private Long idSalle;

    public ActeMedical() {}

    @Override
    public String toString() {
        return "ActeMedical{" +
                "idActeMedical=" + idActeMedical +
                ", idTyeActeMedical=" + idTyeActeMedical +
                ", ordre=" + ordre +
                ", statut='" + statut + '\'' +
                ", idParcours=" + idParcours +
                ", idSalle=" + idSalle +
                '}';
    }
}
