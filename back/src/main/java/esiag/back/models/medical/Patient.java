package esiag.back.models.medical;

import lombok.Data;
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
    private Long idPatient;

    @NotBlank(message = "Veuillez insérer un nom")
    private String nomPatient;

    @NotBlank(message = "Veuillez insérer un prenom")
    private String prenomPatient;

    @NotNull(message = "Veuillez insérer un age")
    @Min(value = 0, message = "L'age doit être positif")
    private Integer agePatient;

    @Column(name = "score_urgence")
    private Integer scoreUrgence;

    @Column(name = "date_arrivee")
    private LocalDateTime dateArrivee;

    @PrePersist
    @PreUpdate
    public void calculerEtMettreAJourScore() {
        this.scoreUrgence = getScoreUrgence();
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


    @Transient
    public int getScoreUrgence() {
        int score = 0;

        if (symptomes != null && !symptomes.isEmpty()) {
            for (String symptome : symptomes) {
                score += calculerPointsSymptome(symptome.trim().toLowerCase());
            }
        }

        score += calculerPointsAge();
        return score;
    }


    private int calculerPointsSymptome(String symptome) {
        switch (symptome) {
            case "fievre_elevee":
                return 3;
            case "douleur_intense":
                return 4;
            case "douleur_thoracique":
            case "difficulte_respiratoire":
                return 5;
            case "perte_connaissance":
                return 5;
            case "hemorragie":
                return 5;
            case "douleur_moderee":
                return 2;
            case "nausee":
                return 1;
            case "fatigue":
                return 1;
            default:
                return 0;
        }
    }


    private int calculerPointsAge() {
        if (agePatient == 1) return 4;
        if (agePatient <= 5) return 3;
        if (agePatient <= 12) return 2;
        if (agePatient <= 17) return 1;
        if (agePatient >= 65) return 3;
        return 0;
    }


    @Transient
    public NiveauUrgence getNiveauUrgence() {
        int score = getScoreUrgence();
        if (score >= 8) return NiveauUrgence.URGENT;
        if (score >= 4) return NiveauUrgence.INTERMEDIAIRE;
        return NiveauUrgence.NON_URGENT;
    }

    public static List<Patient> trierParUrgence(List<Patient> patients) {
        Collections.sort(patients, new Comparator<Patient>() {
            @Override
            public int compare(Patient p1, Patient p2) {
                int niveauCompare = p2.getNiveauUrgence().compareTo(p1.getNiveauUrgence());
                if (niveauCompare != 0) {
                    return niveauCompare;
                }

                int scoreCompare = Integer.compare(p2.getScoreUrgence(), p1.getScoreUrgence());
                if (scoreCompare != 0) {
                    return scoreCompare;
                }

                return p1.getDateArrivee().compareTo(p2.getDateArrivee());           }
        });
        return patients;
    }


    @Override
    public String toString() {
        return "Patient{" +
                "idPatient=" + idPatient +
                ", nomPatient='" + nomPatient + '\'' +
                ", prenomPatient='" + prenomPatient + '\'' +
                ", agePatient=" + agePatient +
                ", symptomes='" + symptomes + '\'' +
                ", scoreUrgence=" + getScoreUrgence() +
                ", niveauUrgence=" + getNiveauUrgence() +
                '}';
    }
}