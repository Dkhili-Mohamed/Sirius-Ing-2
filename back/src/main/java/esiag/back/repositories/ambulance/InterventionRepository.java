package esiag.back.repositories.ambulance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import esiag.back.models.ambulance.Intervention;

@Repository
public interface InterventionRepository extends JpaRepository<Intervention, Long> {
    List<Intervention> findAllByOrderByDateinterventionDesc();
}
