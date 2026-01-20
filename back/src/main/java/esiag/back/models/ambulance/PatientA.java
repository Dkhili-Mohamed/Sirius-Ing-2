package esiag.back.models.ambulance;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "patienta")

public class PatientA {
    @Id
    @Column(name="idpatienta")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idpatientA;

    @Column(name = "nompatienta")
    private String nompatientA;

    @Column(name = "adressepatienta")
    private String adressepatientA;

    @Column(name = "numeropatienta")
    private String numeropatientA;
    
    @Column(name = "patientalatitude")
    private Double patientAlatitude;

    @Column(name = "patientalongitude")
    private Double patientAlongitude;

    public Long getIdpatientA() {
        return idpatientA;
    }

    public String getAdressepatientA() {
        return adressepatientA;
    }

    public String getNumeropatientA() {
        return numeropatientA ;
    }


    public String getNompatientA(){
        return nompatientA ;
    }

    public Double getPatientAlatitude() {
        return patientAlatitude;
    }

    public Double getPatientAlongitude() {
        return patientAlongitude; 
    }
    public void setPatientAlatitude(Double patientAlatitude) {
    this.patientAlatitude = patientAlatitude;
    }
    public void setPatientAlongitude(Double patientAlongitude) {
    this.patientAlongitude = patientAlongitude ;
    }


    @Override
    public String toString() {
        return "patientA{" +
                "idpatientA=" + idpatientA +
                ", nomPatientA=" + nompatientA +
                ", adresssepatientA" + adressepatientA +
                ", numeropatientA" + numeropatientA +
                ", patientAlatitude=" + patientAlatitude +
                ", patientAlongitude=" + patientAlongitude +
                '}';
    }


}
