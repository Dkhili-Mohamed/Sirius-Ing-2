package esiag.back.services;

import esiag.back.models.ambulance.Ambulance;
import esiag.back.repositories.ambulancerepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ambulanceservice {

    @Autowired
    private ambulancerepository ambulancerepository;

    public Ambulance findLastAmbulance() {
        return ambulancerepository.findLastAmbulance();
    }

    
    public Ambulance findById(Long id) {
        Optional<Ambulance> optional = ambulancerepository.findById(id);
        return optional.orElse(null);
    }

    
    public List<Ambulance> findAll() {
        return ambulancerepository.findAll();
    }

   
    public Ambulance save(Ambulance ambulance) {
        return ambulancerepository.save(ambulance);
    }

    
    public boolean delete(Long id) {
        if (ambulancerepository.existsById(id)) {
            ambulancerepository.deleteById(id);
            return true;
        }
        return false;
    }
}
