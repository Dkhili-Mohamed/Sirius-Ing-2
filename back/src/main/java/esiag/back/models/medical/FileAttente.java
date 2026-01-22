package esiag.back.models.medical;

import lombok.Data;
import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Data
@Table(name = "file_attente")
public class FileAttente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_file_attente")
    private Long idFileAttente;

    @Column(name = "date_entree", nullable = false)
    private LocalDateTime dateEntree;

    @Column(nullable = false)
    private Integer rang;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;


    @Override
    public String toString() {
        return "FileAttente{" +
                " id_file_attente =" + idFileAttente +
                ", rang =" + rang +
                ", patient=" + patient.getNomPatient() + " " + patient.getPrenomPatient() +
                ", date_entree =" + dateEntree +
                '}';
    }
}