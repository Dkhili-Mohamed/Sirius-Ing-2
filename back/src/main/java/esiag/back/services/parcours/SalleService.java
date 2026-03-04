package esiag.back.services.parcours;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esiag.back.models.medical.EtatSalle;
import esiag.back.models.medical.Salle;
import esiag.back.repositories.SalleRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SalleService {

    @Autowired
    private SalleRepository salleRepository;


    public boolean updateSalleDecreasePlaceOccupee(Long idSalle) {
        
        Optional<Salle> optionalSalle = salleRepository.findById(idSalle);

        if (optionalSalle.isPresent()){
            Salle salle = optionalSalle.get();
            log.info("Salle trouvée : {}", salle.getNomSalle());
            if(salle.getPlaceOccupee() > 0) {
                salle.setPlaceOccupee(salle.getPlaceOccupee()-1);
                if(salle.getEtatSalle() == EtatSalle.INDISPONIBLE) {
                    salle.setEtatSalle(EtatSalle.DISPONIBLE);
                } 
            }
            
            log.info("Mise à jour de la salle (Decrease): {}", salle.getNomSalle());
            salleRepository.saveAndFlush(salle);
            return true;
        }      

        return false; 
    }

    public boolean updateSalleIncreasePlaceOccupee(Long idSalle) {

        Optional<Salle> optionalSalle = salleRepository.findById(idSalle);
        if(optionalSalle.isPresent()){
            Salle salle = optionalSalle.get();
            log.info("Salle trouvée : {}", salle.getNomSalle());

            salle.setPlaceOccupee(salle.getPlaceOccupee()+1);
            if(salle.getPlaceOccupee() == salle.getCapacite()) {
                salle.setEtatSalle(EtatSalle.INDISPONIBLE);
            }

            log.info("Mise à jour de la salle (Increase) : {}", salle.getNomSalle());
            salleRepository.saveAndFlush(salle);
            return true;
        }
        return false; 
    }


    public List<Salle> findSallesByTypeActeMedical(Long idTypeActeMedical){
        return salleRepository.findSallesByTypeActeMedical(idTypeActeMedical);
    }
    
    public List<Salle> findSallesByEspace(Long idEspace){
        return salleRepository.findSallesByEspace(idEspace);
    }

    public Salle findSalleById(Long idSalle){
        return salleRepository.findById(idSalle).orElse(null);
    }





    
}
