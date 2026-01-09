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
    private Long idFileAttente;

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;

    @Column(name = "rang")
    private int rang;

    @Column(name = "date_entree")
    private LocalDateTime date_entree;


    @Override
    public String toString() {
        return "FileAttente{" +
                " id =" + idFileAttente +
                ", patient=" + patient.getNomPatient() + " " + patient.getPrenomPatient() +
                ", rang =" + rang +
                ", date_entree =" + date_entree +
                '}';
    }
}