package esiag.back.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import esiag.back.models.medical.ActeMedical;

@Repository
public interface ActeMedicalRepository extends JpaRepository<ActeMedical, Long> {
    
    @Query("SELECT a FROM ActeMedical a WHERE a.statut = esiag.back.models.medical.StatutActeMedical.EN_COURS ORDER BY a.ordre")
    List<ActeMedical> findActeMedicalEnCours();
}
