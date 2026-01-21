package esiag.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import esiag.back.models.medical.Salle;

@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {

    @Query( "SELECT sta.salle FROM SalleTypeActeMedical sta WHERE sta.typeActeMedical.idTypeActeMedical = :idTypeActeMedical AND sta.salle.etatSalle = esiag.back.models.medical.EtatSalle.DISPONIBLE")
    List<Salle> findSallesByTypeActeMedical(@Param("idTypeActeMedical") Long idTypeActeMedical);

    @Query("SELECT s FROM Salle s WHERE s.espace.idEspace = :idEspace")
    List<Salle> findSallesByEspace(@Param("idEspace") Long idEspace);

}
