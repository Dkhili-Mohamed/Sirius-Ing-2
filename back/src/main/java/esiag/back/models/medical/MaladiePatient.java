package esiag.back.models.medical;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "maladie_patient")
public class MaladiePatient {

    @Id
    @Column(name="id_maladie_patient")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMaladiePatient;

    @ManyToOne
    @JoinColumn(name = "id_patient", referencedColumnName = "idPatient")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "id_maladie", referencedColumnName = "idMaladie")
    private Maladie maladie;

    @Enumerated(EnumType.STRING)
    @Column(name = "niveauCCMU")
    private NiveauCCMU niveauCCMU;

    @Override
    public String toString() {
        return "DPI{" +
                "idMaladiePatient =" + idMaladiePatient +
                ", idPatient =" + patient +
                ", idMaladie =" + maladie +
                ", niveauCCMU =" + niveauCCMU +
                '}';
    }
}