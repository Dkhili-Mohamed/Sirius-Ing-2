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

    @Column(name = "score_urgence")
    private Integer scoreUrgence;

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
            case "fievreElevee":
                return 3;
            case "douleur_intense":
            case "douleurIntense":
                return 4;
            case "douleur_thoracique":
            case "douleurThoracique":
                return 5;
            case "difficulte_respiratoire":
            case "difficultesRespiratoires":
                return 5;
            case "perte_connaissance":
            case "perteConnaissance":
                return 5;
            case "hemorragie":
            case "saignementAbondant":
                return 5;
            case "douleur_moderee":
            case "douleurModeree":
                return 2;
            case "nausee":
            case "vomissementsPersistants":
                return 1;
            case "fatigue":
            case "fatigueExtreme":
                return 1;
            case "confusion":
                return 2;
            case "frissons":
                return 1;
            case "touxSevere":
                return 2;
            case "malaiseGeneral":
                return 1;
            case "vertigesIntenses":
                return 2;
            case "mauxTeteSeveres":
                return 1;
            case "visionTroublee":
                return 2;
            case "difficultesParole":
                return 3;
            case "faiblesseBrasJambes":
                return 2;
            case "engourdissementFace":
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
 
    public static List<Patient> trierParUrgence(List<Patient> patients) {
        if (patients == null)
            return new ArrayList<>();

        Collections.sort(patients, new Comparator<Patient>() {
            @Override
            public int compare(Patient p1, Patient p2) {
                int n1 = (p1.getNiveauUrgence() != null) ? p1.getNiveauUrgence().ordinal() : -1;
                int n2 = (p2.getNiveauUrgence() != null) ? p2.getNiveauUrgence().ordinal() : -1;

                if (n1 != n2) {
                    return Integer.compare(n2, n1);
                }

                int s1 = p1.getScoreUrgence();
                int s2 = p2.getScoreUrgence();

                if (s1 != s2) {
                    return Integer.compare(s2, s1);
                }

                LocalDateTime d1 = p1.getDateArrivee();
                LocalDateTime d2 = p2.getDateArrivee();

                if (d1 == null && d2 == null)
                    return 0;
                if (d1 == null)
                    return 1;
                if (d2 == null)
                    return -1;

                return d1.compareTo(d2);
            }
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