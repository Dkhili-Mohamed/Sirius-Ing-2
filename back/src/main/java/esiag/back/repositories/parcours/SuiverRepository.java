package esiag.back.repositories.parcours;

import org.springframework.data.jpa.repository.JpaRepository;

import esiag.back.models.medical.Suivre;

public interface SuiverRepository extends JpaRepository<Suivre, Long> { 

}
