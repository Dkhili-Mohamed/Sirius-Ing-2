package esiag.back.models.medical;

import lombok.Data;
import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Data
@Table(name = "maladie")
public class Maladie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_maladie")
    private int idMaladie;

    @Column(name = "nom")
    private String nomMaladie;

    @Column(name = "description")
    private String descriptionMaladie;

    @Column(name = "date_diagnostic")
    private LocalDate dateDiagnostic;

    @Override
    public String toString() {
        return "Maladie{" +
                " id_maladie =" + idMaladie +
                ", nomMaladie =" + nomMaladie +
                ", descriptionMaladie =" + descriptionMaladie +
                ", date_diagnostic =" + dateDiagnostic +
                '}';
    }
}
