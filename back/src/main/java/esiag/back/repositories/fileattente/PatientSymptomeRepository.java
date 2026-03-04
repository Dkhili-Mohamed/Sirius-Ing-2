package esiag.back.repositories.fileattente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import esiag.back.models.medical.PatientSymptome;

public interface PatientSymptomeRepository extends JpaRepository<PatientSymptome, Long> {

    @Query("SELECT patientsymptome FROM PatientSymptome patientsymptome WHERE patientsymptome.patient.idPatient = :idPatient")
    List<PatientSymptome> findByPatientId(@Param("idPatient") Long idPatient);

}
