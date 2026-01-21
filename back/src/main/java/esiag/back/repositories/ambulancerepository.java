package esiag.back.repositories;

import esiag.back.models.ambulance.Ambulance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ambulancerepository extends JpaRepository<Ambulance, Long> {

    
    @Query(value = "SELECT * FROM ambulance ORDER BY idambulance DESC LIMIT 1", nativeQuery = true)
    Ambulance findLastAmbulance();
}
