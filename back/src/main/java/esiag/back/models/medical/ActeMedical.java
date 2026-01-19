package esiag.back.models.medical;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "acte_medical")
public class ActeMedical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acte_medical")
    private Long idActeMedical;

    @Column(name = "ordre")
    private int ordre;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatutActeMedical statut;

    @ManyToOne
    @JoinColumn(name = "id_parcours", nullable = false)
    private Parcours parcours;

    @ManyToOne
    @JoinColumn(name = "id_type_acte_medical", nullable = false)
    private TypeActeMedical typeActeMedical;

    @ManyToOne
    @JoinColumn(name = "id_salle", nullable = true)
    private Salle salle;

    public ActeMedical() {}

    @Override
    public String toString() {
        return "ActeMedical{" +
                "idActeMedical=" + idActeMedical +
                ", ordre=" + ordre +
                ", statut=" + statut +
                ", parcours=" + parcours +
                ", typeActeMedical=" + typeActeMedical +
                ", salle=" + salle +
                '}';
    }
}
