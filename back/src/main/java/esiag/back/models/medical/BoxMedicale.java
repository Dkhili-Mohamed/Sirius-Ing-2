package esiag.back.models.medical;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "box_medicale")
public class BoxMedicale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_box_medicale")
    private Long idBoxMedicale;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @JoinColumn(name = "heure_entree")
    private LocalDateTime heureEntree;

    @JoinColumn(name = "temps_estime")
    private LocalTime tempsEstime;

    @JoinColumn(name = "libre_a")
    private LocalDateTime libreA;

    @JoinColumn(name = "temps_restant", nullable = false)
    private int tempsRestant;


    @Override
    public String toString() {
        return "BoxMedicale{" +
                " id_box_medicale =" + idBoxMedicale +
                ", patient=" + patient.getNomPatient() + " " + patient.getPrenomPatient() +
                ", heure_entree=" + heureEntree +
                ", temps_estime=" + tempsEstime +
                ", libre_a=" +  libreA +
                ", temps_restant=" + tempsRestant +
                '}';
    }
}