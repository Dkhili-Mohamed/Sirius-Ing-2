package esiag.back.repositories;

import esiag.back.models.medical.DPI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DPIRepository extends JpaRepository<DPI, Long> {
    List<DPI> findByPatientIdPatient(Long idPatient);
}