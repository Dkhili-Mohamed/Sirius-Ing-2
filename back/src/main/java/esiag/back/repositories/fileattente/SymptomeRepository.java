package esiag.back.repositories.fileattente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import esiag.back.models.medical.Symptome;

public interface SymptomeRepository extends JpaRepository<Symptome, Long> {

    @Query("SELECT patientSymptome.symptome FROM PatientSymptome patientSymptome WHERE patientSymptome.patient.idPatient = :idPatient")
    List<Symptome> findSymptomeByIdPatient(@Param("idPatient") Long idPatient);


}
