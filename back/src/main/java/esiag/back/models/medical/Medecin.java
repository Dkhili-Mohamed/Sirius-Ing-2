package esiag.back.models.medical;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "medecin")
public class Medecin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicin")
    private Long idMedicin;

    @Column(name = "nom")
    private String nomMedecin;

    @Column(name = "prenom")
    private String prenomMedecin;

    @Column(name = "specialite")
    private String specialiteMedecin;

    public Medecin() {}

    @Override
    public String toString() {
        return "Medecin{" +
                "idMedicin=" + idMedicin +
                ", nomMedecin='" + nomMedecin + '\'' +
                ", prenomMedecin='" + prenomMedecin + '\'' +
                ", specialiteMedecin='" + specialiteMedecin + '\'' +
                '}';
    }
}
