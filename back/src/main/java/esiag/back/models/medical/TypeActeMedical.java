package esiag.back.models.medical;

import lombok.Data;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "type_acte_medical")
public class TypeActeMedical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_acte_medical")
    private Long idTypeActeMedical;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "duree")
    private LocalTime duree;

    public TypeActeMedical() {}

    @Override
    public String toString() {
        return "TypeActeMedical{" +
                "idTypeActeMedical=" + idTypeActeMedical +
                ", libelle='" + libelle + '\'' +
                ", duree=" + duree +
                '}';
    }
}
