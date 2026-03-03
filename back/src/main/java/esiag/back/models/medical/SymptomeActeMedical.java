package esiag.back.models.medical;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "symptome_acte_medical")
@Data
public class SymptomeActeMedical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_symptome_acte_medical")
    private Long idSymptomeActeMedical;

    @ManyToOne
    @JoinColumn(name = "id_symptome", nullable = false)
    private Symptome symptome;

    @ManyToOne
    @JoinColumn(name = "id_type_acte_medical", nullable = false)
    private TypeActeMedical typeActeMedical;

}
