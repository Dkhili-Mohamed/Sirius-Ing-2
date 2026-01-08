package esiag.back.repositories;

import esiag.back.models.medical.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query(value="SELECT * FROM patient AS p ORDER BY p.id_patient DESC LIMIT 1", nativeQuery = true)
    Patient findFirstPatientById();
}