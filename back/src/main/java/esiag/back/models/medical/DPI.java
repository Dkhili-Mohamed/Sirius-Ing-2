package esiag.back.models.medical;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "dpi")
public class DPI {

    @Id
    @Column(name="id_dpi")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDPI;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;

    @Column(name = "antecedent")
    private String antecedent;

    @Column(name = "traitement")
    private String traitement;

    @Override
    public String toString() {
        return "DPI{" +
                "idDPI=" + idDPI +
                ", patient=" + (patient != null ? patient.getNomPatient() + " " + patient.getPrenomPatient() : "null") +
                ", antecedent =" + antecedent +
                ", traitement =" + traitement +
                '}';
    }
}
