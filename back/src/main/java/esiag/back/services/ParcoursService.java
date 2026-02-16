package esiag.back.services;

import esiag.back.models.dto.ParcoursPatient;
import esiag.back.models.dto.PatientStatutParcours;
import esiag.back.models.medical.ActeMedical;
import esiag.back.models.medical.Patient;
import esiag.back.models.medical.StatutActeMedical;
import esiag.back.models.medical.Parcours;
import esiag.back.repositories.ActeMedicalRepository;
import esiag.back.repositories.ParcoursRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class ParcoursService {

    @Autowired
    private ParcoursRepository parcoursRepository;

    @Autowired
    private ActeMedicalRepository acteMedicalRepository;

    public List<ParcoursPatient> findParcoursPatientById(Long idPatient){
        return parcoursRepository.getParcoursByPatientId(idPatient);
    }

    public List<ParcoursPatient> findHistoriqueParcoursPatients(Long idPatient){
        return parcoursRepository.getHistoriqueParcoursByPatientId(idPatient);
    }

    public List<ActeMedical> findAllParcoursPatient(){
        return acteMedicalRepository.findAll();
    }

    public List<PatientStatutParcours> findAllPatientStatutParcours() {
        return parcoursRepository.findAllPatientStatutParcours();
    }

    public boolean updateStatutParcoursToEnCours(Long idParcours){

        Optional<Parcours> optionalParcours = parcoursRepository.findById(idParcours);

        if(optionalParcours.isPresent()){
            Parcours parcours = optionalParcours.get();

            parcours.setStatutGlobal(StatutActeMedical.EN_COURS);

            parcoursRepository.saveAndFlush(parcours);
            return true;
        }

        return false;

    }

    public boolean updateStatutParcoursToTermine(Long idParcours){
                Optional<Parcours> optionalParcours = parcoursRepository.findById(idParcours);

        if(optionalParcours.isPresent()){
            Parcours parcours = optionalParcours.get();

            parcours.setStatutGlobal(StatutActeMedical.TERMINE);

            parcoursRepository.saveAndFlush(parcours);
            return true;
        }

        return false;
    }

}
