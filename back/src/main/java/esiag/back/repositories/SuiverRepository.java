package esiag.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import esiag.back.models.medical.Suivre;

public interface SuiverRepository extends JpaRepository<Suivre, Long> { 

}
