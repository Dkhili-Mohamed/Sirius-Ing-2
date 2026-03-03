package esiag.back.models.medical;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "symptome")
public class Symptome {

    @Id
    @Column(name = "id_symptome")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSymptome;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "score")
    private int score;

    @Override
    public String toString() {
        return "Symptome [idSymptome=" + idSymptome + ", libelle=" + libelle + ", score=" + score + "]";
    }

}
