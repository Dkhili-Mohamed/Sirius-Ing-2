package esiag.back.models.ambulance;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Table(name = "intervention")
public class Intervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idintervention")
    private Long idintervention;

    @Column(name = "nomintervention")
    private String nomintervention;

    @Column(name = "adresseintervention")
    private String adresseintervention;

    @Column(name = "interventionstatut")
    private String interventionstatut;

    @Column(name = "dateintervention", insertable = false, updatable = false)
    private LocalDate dateintervention;

    public Long getIdintervention() {
        return idintervention;
    }

    public String getNomintervention() {
        return nomintervention;
    }

    public String getAdresseintervention() {
        return adresseintervention;
    }

    public String getInterventionstatut() {
        return interventionstatut;
    }

    public LocalDate getDateintervention() {
        return dateintervention;
    }

    public void setNomintervention(String nomintervention) {
        this.nomintervention = nomintervention;
    }

    public void setAdresseintervention(String adresseintervention) {
        this.adresseintervention = adresseintervention;
    }

    public void setInterventionstatut(String interventionstatut) {
        this.interventionstatut = interventionstatut;
    }

    @Override
    public String toString() {
        return "Intervention{" +
                "idintervention=" + idintervention +
                ", nomintervention=" + nomintervention +
                ", adresseintervention=" + adresseintervention +
                ", interventionstatut=" + interventionstatut +
                ", dateintervention=" + dateintervention +
                '}';
    }
}