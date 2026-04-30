package esiag.back.models.medical;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Data
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patient")
    private Long idPatient;

    @NotBlank(message = "Veuillez insérer un nom")
    @Column(name ="nom_patient")
    private String nomPatient;

    @NotBlank(message = "Veuillez insérer un prenom")
    @Column(name = "prenom_patient")
    private String prenomPatient;

    @NotNull(message = "Veuillez insérer un age")
    @Min(value = 0, message = "L'age doit être positif")
    @Column(name = "age_patient")
    private Integer agePatient;

    @Column(name = "statut_patient")
    private StatutPatient statutPatient;
    @Transient
    private List<Long> patient_symptomes;


@PrePersist
public void statutPatient() {
    if(this.statutPatient == null) {
        this.statutPatient = StatutPatient.NON_CONSULTE;
    }
}
    @Override
    public String toString() {
        return "Patient{" +
                "idPatient=" + idPatient +
                ", nomPatient='" + nomPatient + '\'' +
                ", prenomPatient='" + prenomPatient + '\'' +
                ", agePatient=" + agePatient +
                ", statutPatient=" + statutPatient +
//               ", symptomes='" + symptomes + '\'' +
//                ", date_arrivee='" + dateArrivee+
                '}';
    }
}