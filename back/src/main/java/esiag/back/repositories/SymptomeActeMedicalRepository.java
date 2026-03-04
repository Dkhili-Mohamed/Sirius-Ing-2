package esiag.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import esiag.back.models.medical.SymptomeActeMedical;

public interface SymptomeActeMedicalRepository extends JpaRepository<SymptomeActeMedical, Long> {

    
}
