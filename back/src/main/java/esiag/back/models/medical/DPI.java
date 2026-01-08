package esiag.back.models.medical;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "dpi")
public class DPI {

    @Id
    @Column(name="id_dpi")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDPI;

    @Column(name="id_patient")
    private int idPatient;

    @Column(name="id_maladie")
    private int idMaladie;

    @Column(name = "antecedent")
    private String antecedent;

    @Column(name = "traitement")
    private String traitement;

    @Override
    public String toString() {
        return "DPI{" +
                "idDPI=" + idDPI +
                ", idPatient =" + idPatient +
                ", idMaladie =" + idMaladie +
                ", antecedent =" + antecedent +
                ", traitement =" + traitement +
                '}';
    }
}
