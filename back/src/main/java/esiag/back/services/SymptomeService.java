package esiag.back.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esiag.back.models.medical.Symptome;
import esiag.back.models.medical.SymptomeRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SymptomeService {

    @Autowired
    private SymptomeRepository symptomeRepository;

    List<Symptome> findSymptomeByIdPatient(Long idPatient){
        return symptomeRepository.findSymptomeByIdPatient(idPatient);
    }

}
