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
public class Ambulance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    @Column(name = "idambulance")
    private Long idambulance;

    @Column(name = "adresseambulance")
    private String adresseambulance;

    @Column(name = "disponibiliteambulance")
    private Boolean disponibiliteambulance;

    @Column(name = "vitessemoyambulance")
    private Double vitessemoyambulance;

    @Column(name = "equipementambulance")
    private Double equipementambulance;

    @Column(name = "experienceambulance")
    private Double experienceambulance;
    
    @Column(name = "ambulancelatitude")
    private Double ambulancelatitude;

    @Column(name = "ambulancelongitude")
    private Double ambulancelongitude;

    @Column(name = "ambulancedistance")
    private Double ambulancedistance;

    @Column(name = "tempstrajet")
    private Double tempstrajet;

    @Column(name = "notetrajet")
    private Double notetrajet;

    @Column(name = "noteglobale")
    private Double noteglobale;





    public Long getIdambulance() {
        return idambulance;
    }

    public String getAdresseambulance() {
        return adresseambulance;
    }

    public Boolean getDisponibiliteambulance() {
        return disponibiliteambulance;
    }

    public Double getVitessemoyambulance() {
        return vitessemoyambulance;
    }

    public Double getEquipementambulance() {
        return equipementambulance;
    }

    public Double getExperienceambulance() {
        return experienceambulance;
    }

    public Double getAmbulancelatitude() {
        return ambulancelatitude;
    }

    public Double getAmbulancelongitude() {
        return ambulancelongitude;
    }
    public Double getAmbulancedistance() {
        return ambulancedistance;
    }
    public Double getTempstrajet() {
        return tempstrajet;
    }
    public Double getNotetrajet() {
        return notetrajet;
    }
    public Double getNoteglobale() {
        return noteglobale;
    }

    //Les setters
    public void setAmbulancelatitude(Double ambulancelatitude) {
    this.ambulancelatitude = ambulancelatitude;
    }
    public void setAmbulancelongitude(Double ambulancelongitude) {
    this.ambulancelongitude = ambulancelongitude;
    }
    public void setAdresseambulance(String adresseambulance) {
        this.adresseambulance = adresseambulance;
    }

    public void setDisponibiliteambulance(Boolean disponibiliteambulance) {
        this.disponibiliteambulance = disponibiliteambulance;
    }

    public void setVitessemoyambulance(Double vitessemoyambulance) {
        this.vitessemoyambulance = vitessemoyambulance;
    }

    public void setEquipementambulance(Double equipementambulance) {
        this.equipementambulance = equipementambulance;
    }

    public void setExperienceambulance(Double experienceambulance) {
        this.experienceambulance = experienceambulance;
    }

    public void setAmbulancedistance(Double ambulancedistance) {
        this.ambulancedistance = ambulancedistance;
    }

    public void setTempstrajet(Double tempstrajet) {
        this.tempstrajet = tempstrajet;
    }

    public void setNotetrajet(Double notetrajet) {
        this.notetrajet = notetrajet;
    }
    public void setNoteglobale(Double noteglobale) {
        this.noteglobale = noteglobale;
    }


    

    @Override
    public String toString() {
        return "Ambulance{" +
                "idAmbulance=" + idambulance +
                ", adresseAmbulance='" + adresseambulance + '\'' +
                ", disponibiliteAmbulance=" + disponibiliteambulance +
                ", vitessemoyAmbulance=" + vitessemoyambulance +
                ", equipementAmbulance=" + equipementambulance +
                ", experienceAmbulance=" + experienceambulance +
                ", ambulancelatitude=" + ambulancelatitude +
                ", ambulancelongitude=" + ambulancelongitude +
                ", tempstrajet=" + tempstrajet +
                ", notetrajet=" + notetrajet +
                ", notetrajet=" + noteglobale +
                '}';
    }
}
