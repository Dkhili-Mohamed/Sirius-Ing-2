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
@Table(name = "urgence")
public class urgence {
    @Id
    @Column(name="idurgence")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idurgence;

    @Column(name = "nomurgence")
    private String nomurgence;

    @Column(name = "adresseurgence")
    private String adresseurgence;
     @Column(name = "urgencelatitude")
    private Double urgencelatitude;

    @Column(name = "urgencelongitude")
    private Double urgencelongitude;

    @Override
    public String toString() {
        return "urgence{" +
                "idurgence=" + idurgence +
                ", nomurgence=" + nomurgence +
                ", adresseurgence" + adresseurgence+
                ", urgencelatitude=" + urgencelatitude +
                ", urgencelongitude=" + urgencelongitude +
                '}';
    }

    


}