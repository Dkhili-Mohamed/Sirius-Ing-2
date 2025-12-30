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

    @Column(name = "nom")
    private String nomPatient;

    @Column(name = "prenom")
    private String prenomPatient;

    @Column(name = "age")
    private Integer agePatient;

    @Override
    public String toString() {
        return "Patient{" +
                "idPatient=" + idPatient +
                ", nomPatient='" + nomPatient + '\'' +
                ", prenomPatient='" + prenomPatient + '\'' +
                ", agePatient=" + agePatient +
                '}';
    }
}
