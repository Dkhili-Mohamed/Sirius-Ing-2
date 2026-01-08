package esiag.back.models.medical;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<MaladiePatient> maladies;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<DPI> dpis;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat_sante")
    private EtatSante etat_sante;

    @Override
    public String toString() {
        return "Patient{" +
                "idPatient=" + idPatient +
                ", nomPatient='" + nomPatient + '\'' +
                ", prenomPatient='" + prenomPatient + '\'' +
                ", agePatient=" + agePatient +
                ", etat_sante =" + etat_sante +
                '}';
    }
}
