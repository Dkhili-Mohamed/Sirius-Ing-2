package esiag.back.models.medical;


import lombok.Data;

import java.util.Comparator;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

import java.time.LocalDateTime;

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

    @Column(name = "date_arrivee")
    private LocalDateTime dateArrivee;

    @Transient
    private List<String> patient_symptomes;


@PrePersist
public void copierSymptomesEtDate() {
    System.out.println("patient_symptomes reçu: " + patient_symptomes);

    if (patient_symptomes != null && !patient_symptomes.isEmpty()) {

        this.symptomes.clear();
        this.symptomes.addAll(patient_symptomes);
    } else {
        System.out.println("patient_symptomes est null ou vide !");
    }

    if (this.dateArrivee == null) {
        this.dateArrivee = LocalDateTime.now();
    }
}

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "patient_symptomes",
            joinColumns = @JoinColumn(name = "id_patient")
    )
    @Column(name = "symptome", nullable = false)
    @Size(min = 1, message = "Veuillez insérer au moins un symptome")
    private Set<String> symptomes = new HashSet<>();

    public void addSymptome(String symptome) {
        this.symptomes.add(symptome);
    }


    @Override
    public String toString() {
        return "Patient{" +
                "idPatient=" + idPatient +
                ", nomPatient='" + nomPatient + '\'' +
                ", prenomPatient='" + prenomPatient + '\'' +
                ", agePatient=" + agePatient +
                ", symptomes='" + symptomes + '\'' +
                '}';
    }
}