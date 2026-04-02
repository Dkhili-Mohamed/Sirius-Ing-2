package esiag.back.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import esiag.back.models.ambulance.Intervention;


@Repository
public interface InterventionRepository extends JpaRepository<Intervention, Long> {
    List<Intervention> findAllByOrderByDateinterventionDesc();
    // Vérifie si une intervention avec une adresse et un statut spécifiques existe déjà pour eviter d'ajouter plusieurs fois la meme intervention dans l'historique
    boolean existsByAdresseinterventionAndInterventionstatut(String adresse, String statut);
}
