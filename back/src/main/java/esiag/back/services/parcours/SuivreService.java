package esiag.back.services.parcours;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esiag.back.models.medical.Suivre;
import esiag.back.repositories.SuiverRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SuivreService {

    @Autowired
    private SuiverRepository suiverRepository;

    public Suivre insertSuivre(Suivre suivre) {

        log.info("Insertion nouvelle ligne suivre : idPatient={}, idParcours={}", 
                suivre.getPatient().getIdPatient(), suivre.getParcours().getIdParcours());
        return suiverRepository.save(suivre);
        
    }

    

}
