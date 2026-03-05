package esiag.back.models.medical;

import java.time.LocalDate;

import javax.persistence.*;


import lombok.Data;

@Entity
@Table(name = "patient_symptome")
@Data
public class PatientSymptome {

    @Id
    @Column(name = "id_patient_symptome")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPatientSymptome;

    @ManyToOne
    @JoinColumn(name = "id_patient", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "id_symptome", nullable = false)
    private Symptome symptome;


    @Column(name = "date_symptome")
    private LocalDate dateSymptome;

    @Override
    public String toString() {
        return "PatientSymptome [idPatientSymptome=" + idPatientSymptome + ", patient=" + patient + ", symptome="
                + symptome + ", dateSymptome=" + dateSymptome + "]";
    }

}
