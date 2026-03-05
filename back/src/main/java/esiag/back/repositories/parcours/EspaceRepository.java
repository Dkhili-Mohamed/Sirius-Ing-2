package esiag.back.repositories.parcours;

import esiag.back.models.architecture.Espace;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EspaceRepository extends JpaRepository<Espace, Long> {
    
    @Query("SELECT e FROM Espace e WHERE e.numeroEspace = :string")
    List<Espace> findByNumeroEspace(@Param("string") String string);
}
