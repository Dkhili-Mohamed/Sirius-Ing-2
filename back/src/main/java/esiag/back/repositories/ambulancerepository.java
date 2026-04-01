package esiag.back.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import esiag.back.models.ambulance.Ambulance;

@Repository
public interface ambulancerepository extends JpaRepository<Ambulance, Long> {

    
    @Query(value = "SELECT * FROM ambulance ORDER BY idambulance DESC LIMIT 1", nativeQuery = true)
    Ambulance findLastAmbulance();

    @Query(value = "SELECT * FROM ambulance WHERE disponibiliteambulance = true ORDER BY noteglobale DESC", nativeQuery = true)
    List<Ambulance> findAvailableAmbulances();
}
