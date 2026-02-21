package esiag.back.repositories;

import esiag.back.models.medical.BoxMedicale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxMedicaleRepository extends JpaRepository<BoxMedicale,Long> {

}