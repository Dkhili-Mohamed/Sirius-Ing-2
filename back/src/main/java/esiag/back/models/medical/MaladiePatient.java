package esiag.back.models.medical;

import esiag.back.models.SampleType;
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


    @Column(name="id_patient")
    private int idPatient;

    @Column(name = "id_maladie")
    private int idMaladie;

    @Enumerated(EnumType.STRING)
    @Column(name = "niveauCCMU")
    private NiveauCCMU niveauCCMU;

    @Override
    public String toString() {
        return "DPI{" +
                "idMaladiePatient =" + idMaladiePatient +
                ", idPatient =" + idPatient +
                ", idMaladie =" + idMaladie +
                ", niveauCCMU =" + niveauCCMU +
                '}';
    }
}