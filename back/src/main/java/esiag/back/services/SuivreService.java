package esiag.back.services;

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

    public boolean insertSuivre(Long idPatient, Long idParcours) {

        Suivre suivre = new Suivre();
        if (idPatient != null && idParcours != null) {

            suivre.getPatient().setIdPatient(idPatient);
            suivre.getParcours().setIdParcours(idParcours);

            suiverRepository.saveAndFlush(suivre);

            log.info("Insertion nouvelle ligne suivre : idPatient={}, idParcours={}", idPatient, idParcours);

            return true;
        }

        return true;
    }

    

}
