package esiag.back.repositories;

import esiag.back.models.medical.MaladiePatient;
import esiag.back.models.medical.Patient;
import esiag.back.models.medical.Maladie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MaladiePatientRepository extends JpaRepository<MaladiePatient, Long> {
    Optional<MaladiePatient> findByPatientAndMaladie(Patient patient, Maladie maladie);
}
