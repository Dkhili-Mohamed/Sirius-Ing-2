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
    private Long idDPI;

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
                ", idPatient =" + patient +
                ", antecedent =" + antecedent +
                ", traitement =" + traitement +
                '}';
    }
}
