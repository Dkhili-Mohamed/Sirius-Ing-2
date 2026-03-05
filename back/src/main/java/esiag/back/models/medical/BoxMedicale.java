package esiag.back.models.medical;

import lombok.Data;

import javax.persistence.*;
import java.time.Duration;
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

    @JoinColumn(name = "nom_box")
    private String nomBox;

    @ManyToOne
    @JoinColumn(name = "patient")
    private Patient patient;

    @JoinColumn(name = "statut_box")
    @Enumerated(EnumType.STRING)
    private StatutBox statutBox = StatutBox.LIBRE;

    @JoinColumn(name = "heure_entree")
    private LocalDateTime heureEntree;

    @JoinColumn(name = "temps_estime")
    private LocalTime tempsEstime;

    @JoinColumn(name = "libre_a")
    private LocalDateTime libreA;

    //@JoinColumn(name = "temps_restant", nullable = false)
    @Transient
    private int tempsRestant;

    public int getTempsRestant() {
        if(libreA == null)
            return 0;
        Duration tempsRestant = Duration.between(LocalDateTime.now(), libreA);
        return Math.max(0, (int) tempsRestant.toSeconds());
    }

    @PrePersist
    public void attribuerNomBox() {
        if (this.nomBox == null || this.nomBox.trim().isEmpty()) {
            this.nomBox = "Box " + this.idBoxMedicale;
        }
    }


    @Override
    public String toString() {
        return "BoxMedicale{" +
                " id_box_medicale =" + idBoxMedicale +
                ", nom_box =" + nomBox +
                ", patient=" + patient.getNomPatient() + " " + patient.getPrenomPatient() +
                ", heure_entree=" + heureEntree +
                ", temps_estime=" + tempsEstime +
                ", libre_a=" +  libreA +
                ", temps_restant=" + tempsRestant +
                '}';
    }
}