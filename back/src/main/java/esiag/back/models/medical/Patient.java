package esiag.back.models.medical;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private EtatSante etatSante = EtatSante.SAIN;

    @Override
    public String toString() {
        return "Patient{" +
                "idPatient=" + idPatient +
                ", nomPatient='" + nomPatient + '\'' +
                ", prenomPatient='" + prenomPatient + '\'' +
                ", agePatient=" + agePatient +
                ", etat_sante =" + etatSante +
                '}';
    }
}
