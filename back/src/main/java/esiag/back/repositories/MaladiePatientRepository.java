package esiag.back.repositories;

import esiag.back.models.medical.MaladiePatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaladiePatientRepository extends JpaRepository<MaladiePatient, Long> {

}