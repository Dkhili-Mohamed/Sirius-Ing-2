package esiag.back.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esiag.back.models.dto.ParcoursPatient;
import esiag.back.models.medical.ActeMedical;
import esiag.back.models.medical.Salle;
import esiag.back.models.medical.StatutActeMedical;
import esiag.back.repositories.ActeMedicalRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ActeMedicalService {
    
    @Autowired
    private ActeMedicalRepository acteMedicalRepository;
    @Autowired
    private SalleService salleService;

    public boolean updateStatutActeMedicalToEncours(Long idActeMedical, Long idSalle) {

        Optional<ActeMedical> optionalActeMedical = acteMedicalRepository.findById(idActeMedical);
        Salle salle = salleService.findSalleById(idSalle);
        
        if (optionalActeMedical.isPresent() && salle != null){
            ActeMedical acteMedical = optionalActeMedical.get();
            
            acteMedical.setStatut(StatutActeMedical.EN_COURS);
            acteMedical.setSalle(salle);
            log.info("Mise à jour de l'acte médical (En cours) : {}", acteMedical.getIdActeMedical());
            acteMedicalRepository.saveAndFlush(acteMedical);
            return true;
        }  
       
        return false; 

    }


    public boolean updatStatutActeMedicalToTermine() {

        List<ActeMedical> acteMedicals = acteMedicalRepository.findActeMedicalEnCours();

    
        if (acteMedicals != null && !acteMedicals.isEmpty()){

            ActeMedical acteMedical = acteMedicals.get(0);

            acteMedical.setStatut(StatutActeMedical.TERMINE);

            log.info("Mise à jour de l'acte médical (Terminé) : {}", acteMedical.getIdActeMedical());
            acteMedicalRepository.saveAndFlush(acteMedical);
            return true;
        }
        
        return false; 
    }


    public boolean updateStatutPremierActeMedical(ParcoursPatient parcoursPatient) {

        Optional<ActeMedical> optionalActeMedical = acteMedicalRepository.findById(parcoursPatient.getIdActeMedical());

        if (optionalActeMedical.isPresent()){
            ActeMedical acteMedical = optionalActeMedical.get();
            
            acteMedical.setStatut(StatutActeMedical.EN_COURS);
            log.info("Mise à jour du premier acte médical (En cours) : {}", acteMedical.getIdActeMedical());
            acteMedicalRepository.saveAndFlush(acteMedical);
            return true;
        }  
       
        return false; 

    }

    public List<ActeMedical> findActeMedicalsByOrdre(int ordre, Long idParcours){
        return acteMedicalRepository.findActeMedicalsByOrdre(ordre, idParcours);
    }

    public List<ActeMedical> findAllActeMedicalsByParours(Long idParcours){
        return acteMedicalRepository.findAllActeMedicalsByParours(idParcours);
    }

}
