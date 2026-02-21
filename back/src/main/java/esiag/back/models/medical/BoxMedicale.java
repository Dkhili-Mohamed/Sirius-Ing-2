package esiag.back.models.medical;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "box_medicale")
public class BoxMedicale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_box_medicale")
    private Long idBoxMedicale;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;


    @Override
    public String toString() {
        return "BoxMedicale{" +
                " id_box_medicale =" + idBoxMedicale +
                ", patient=" + patient.getNomPatient() + " " + patient.getPrenomPatient() +
                '}';
    }
}