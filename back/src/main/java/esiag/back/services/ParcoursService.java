package esiag.back.services;

import esiag.back.models.dto.ParcoursPatient;
import esiag.back.models.medical.ActeMedical;
import esiag.back.repositories.ParcoursRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ParcoursService {

    @Autowired
    private ParcoursRepository parcoursRepository;

    public List<ParcoursPatient> findParcoursPatientById(Long idPatient){
        return parcoursRepository.getParcoursByPatientId(idPatient);
    }

    public List<ActeMedical> findAllParcoursPatient(){
        return parcoursRepository.findAll();
    }
}
