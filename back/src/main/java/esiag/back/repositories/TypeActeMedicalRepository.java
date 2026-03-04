package esiag.back.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import esiag.back.models.medical.TypeActeMedical;

public interface TypeActeMedicalRepository extends JpaRepository<TypeActeMedical, Long> {

    @Query("SELECT sa.typeActeMedical FROM SymptomeActeMedical sa WHERE sa.symptome.idSymptome = :idSymptome")
    List<TypeActeMedical> findSymptomesByActeMedicalId(@Param("idSymptome") Long idSymptome);

}
