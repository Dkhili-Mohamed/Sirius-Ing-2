package esiag.back.models.ambulance;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Entity
@Data
@Table(name = "patientA")

public class PatientA {
    @Id
    @Column(name="idpatientA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idpatientA;

    @Column(name = "nompatientA")
    private String nompatientA;

    @Column(name = "adressepatientA")
    private String adressepatientA;

    @Column(name = "numeropatientA")
    private String numeropatientA;

    @Override
    public String toString() {
        return "patientA{" +
                "idpatientA=" + idpatientA +
                ", nomPatientA=" + nompatientA +
                ", adresssepatientA" + adressepatientA +
                ", numeropatientA" + numeropatientA +
                '}';
    }


}
