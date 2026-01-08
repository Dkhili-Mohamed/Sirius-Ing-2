package esiag.back.models.medical;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "maladie_patient")
public class MaladiePatient {

    @Id
    @Column(name="id_maladie_patient")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMaladiePatient;

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "id_maladie")
    private Maladie maladie;

    @Enumerated(EnumType.STRING)
    @Column(name = "niveauCCMU")
    private NiveauCCMU niveauCCMU;

    @Column(name = "date_diagnostic")
    private LocalDateTime dateDiagnostic;

    @Override
    public String toString() {
        return "DPI{" +
                "idMaladiePatient =" + idMaladiePatient +
                ", idPatient =" + patient +
                ", idMaladie =" + maladie +
                ", niveauCCMU =" + niveauCCMU +
                ", date_diagnostic =" + dateDiagnostic +
                '}';
    }
}