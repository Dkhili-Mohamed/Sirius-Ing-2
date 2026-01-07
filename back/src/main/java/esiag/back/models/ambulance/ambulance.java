package esiag.back.models.ambulance;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Entity
@Data
@Table(name = "ambulance")
public class ambulance {
    @Id
    @Column(name="id_ambulance")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAmbulance;

    @Column(name = "disponibilite_ambulance")
    private boolean disponibiliteAmbulance;

    @Column(name = "adresseambulance")
    private double adresseambulance;

    @Column(name = "vitessemoy_ambulance")
    private float vitessemoyAmbulance;

    @Column(name = "equipement_ambulance")
    private int equipementAmbulance;

    @Column(name = "experience_ambulance")
    private float experienceAmbulance;

    @Override
    public String toString() {
        return "ambulance{" +
                "idambulance=" + idAmbulance +
                ", adresseambulance=" + adresseambulance +
                ", vitessemoyambulance" + vitessemoyAmbulance +
                ", equipementambulance" + equipementAmbulance +
                ", experienceambulance" + experienceAmbulance +
                '}';
    }


}
