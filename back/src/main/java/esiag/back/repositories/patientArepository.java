package esiag.back.repositories;

import esiag.back.models.ambulance.PatientA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import esiag.back.models.ambulance.PatientA;

@Repository
public interface patientArepository extends JpaRepository<PatientA, Long> {

    // 🔹 Récupérer le dernier patientA ajouté (ordre décroissant par ID)
    @Query(value = "SELECT * FROM patientA AS p ORDER BY p.idpatientA DESC LIMIT 1", nativeQuery = true)
    PatientA findLastPatientA();
}
