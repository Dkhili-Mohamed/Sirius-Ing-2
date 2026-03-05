package esiag.back.repositories.fileattente;

import esiag.back.models.medical.BoxMedicale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoxMedicaleRepository extends JpaRepository<BoxMedicale,Long> {

    @Query("SELECT b FROM BoxMedicale b WHERE b.statutBox = 'LIBRE' ORDER BY b.nomBox ASC")
    List<BoxMedicale> findBoxMedicaleLibre();

    @Query("SELECT b FROM BoxMedicale b WHERE b.statutBox = 'OCCUPEE' ORDER BY b.nomBox ASC")
    List<BoxMedicale> findBoxMedicaleOccupee();

    @Query("SELECT COUNT(b) FROM BoxMedicale b")
    long countBoxMedicale();

    @Query("SELECT b FROM BoxMedicale b WHERE b.nomBox IS NULL")
    List<BoxMedicale> findBoxMedicaleSansNom();
}