package esiag.back.models.medical;

import lombok.Data;

import javax.persistence.*;
import java.time.Duration;

@Entity
@Data
@Table(name = "typeactemedical")
public class TypeActeMedical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_acte_medical")
    private Long idTyeActeMedical;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "duree")
    private Duration duree;

    public TypeActeMedical() {}

    @Override
    public String toString() {
        return "TypeActeMedical{" +
                "idTyeActeMedical=" + idTyeActeMedical +
                ", libelle='" + libelle + '\'' +
                ", duree=" + duree +
                '}';
    }
}
