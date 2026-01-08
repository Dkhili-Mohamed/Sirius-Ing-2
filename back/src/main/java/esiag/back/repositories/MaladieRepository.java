package esiag.back.repositories;

import esiag.back.models.medical.Maladie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaladieRepository extends JpaRepository<Maladie, Long> {
    Optional<Maladie> findByNomMaladie(String nomMaladie);


}