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
@Table(name = "ambulancelogin")
public class AmbulanceLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "idambulancelogin")
    private Long idambulancelogin;
    @Column(name = "ambulanceloginadresse")
    private String ambulanceloginadresse;
    @Column(name = "ambulanceloginmdps")
    private String ambulanceloginmdps;



    
    public Long getIdambulancelogin() {
        return idambulancelogin;
    }

    public String getAmbulanceloginadresse() {
        return ambulanceloginadresse;
    }

    public String getAmbulanceloginmdps() {
        return ambulanceloginmdps;
    }

    


    public void setAmbulanceloginadresse(String ambulanceloginadresse) {
        this.ambulanceloginadresse = ambulanceloginadresse;
    }

    public void setAmbulanceloginmdps(String ambulanceloginmdps) {
        this.ambulanceloginmdps = ambulanceloginmdps;
    }

    @Override
    public String toString() {
        return "AmbulanceLogin{" +
                "idambulancelogin=" + idambulancelogin +
                ", ambulanceloginadresse='" + ambulanceloginadresse  +
                ", ambulanceloginmdps='" + ambulanceloginmdps  +
                '}';
    }
}