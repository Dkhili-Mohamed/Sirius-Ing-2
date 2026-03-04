package esiag.back.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esiag.back.models.medical.Symptome;
import esiag.back.models.medical.TypeActeMedical;
import esiag.back.repositories.TypeActeMedicalRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class TypeActeMedicalService {

    @Autowired
    private TypeActeMedicalRepository typeActeMedicalRepository;

    @Autowired
    private SymptomeService symptomeService;


    public List<TypeActeMedical> findTypeActeMedicalBySymptomePatient(Long idSymptome){
        return typeActeMedicalRepository.findSymptomesByActeMedicalId(idSymptome);
    }

    public List<TypeActeMedical> findAllTypeActeMedicalBySymptomePatient(Long idPatient){
        List<TypeActeMedical> typeActeMedicalsPatient = new ArrayList<>();

        List<Symptome> symptomesPatient = symptomeService.findSymptomeByIdPatient(idPatient);
        
        if (symptomesPatient != null || !symptomesPatient.isEmpty()) {

            for (Symptome symptome : symptomesPatient) {

                List<TypeActeMedical> typeActeMedicals = findTypeActeMedicalBySymptomePatient(symptome.getIdSymptome());

                if (typeActeMedicals != null || !typeActeMedicals.isEmpty()) {
                    for (TypeActeMedical typeActeMedical : typeActeMedicals) {
                        if (!typeActeMedicalsPatient.contains(typeActeMedical)) {
                            typeActeMedicalsPatient.add(typeActeMedical);
                        }
                    }
                }

            }
        }    
            
        return typeActeMedicalsPatient;  
    }


}
