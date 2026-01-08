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

    @Column(name = "date_diagnostic")
    private LocalDate dateDiagnostic;

    @Column(name = "nom")
    private String nomMaladie;

    @Column(name = "description")
    private String descriptionMaladie;

    @Override
    public String toString() {
        return "Maladie{" +
                " id_maladie =" + idMaladie +
                ", date_diagnostic =" + dateDiagnostic +
                ", nom =" + nomMaladie +
                ", description =" + descriptionMaladie +
                '}';
    }
}
