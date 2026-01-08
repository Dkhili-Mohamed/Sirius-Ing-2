package esiag.back.models.medical;

import lombok.Data;
import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Data
@Table(name = "file_attente")
public class FileAttente {

    @Id
    @Column(name="id_patient")
    private int idPatient;

    @Column(name = "rang")
    private int rang;

    @Column(name = "nom")
    private String nom_Patient;

    @Column(name = "prenom")
    private String prenom_Patient;

    @Column(name = "date_entree")
    private LocalDateTime date_entree;


    @Override
    public String toString() {
        return "DPI{" +
                ", idPatient =" + idPatient +
                ", rang =" + rang +
                ", nom =" + nom_Patient +
                ", prenom =" + prenom_Patient +
                ", date_entree =" + date_entree +
                '}';
    }
}