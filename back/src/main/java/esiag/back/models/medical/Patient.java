package esiag.back.models.medical;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patient")
    private Long idPatient;

    @Column(name = "nom", nullable = false)
    private String nomPatient;

    @Column(name = "prenom", nullable = false)
    private String prenomPatient;

    @Column(name = "age", nullable = false)
    private int agePatient;


    @Column(name = "symptomes")
    private String symptomes;


    @Transient
    public int getScoreUrgence() {
        int score = 0;

        if (symptomes != null && !symptomes.isEmpty()) {
            String[] symptomesArray = symptomes.split(",");
            for (String symptome : symptomesArray) {
                score += calculerPointsSymptome(symptome.trim().toLowerCase());
            }
        }


        score += calculerPointsAge();

        return score;
    }


    private int calculerPointsSymptome(String symptome) {
        switch (symptome) {
            case "fievre":
                return 3;
            case "douleur_thoracique":
            case "difficulte_respiratoire":
                return 5;
            case "nausee":
                return 1;
            case "vomissements":
            case "cephalee":
                return 2;
            case "douleur_abdominale":
                return 3;
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