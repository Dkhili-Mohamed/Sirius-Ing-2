package esiag.back.services;

import esiag.back.models.ambulance.PatientA;
import esiag.back.repositories.patientArepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class patientAservice {

    @Autowired
    private patientArepository patientARepository;

    
    public PatientA findLastPatientA() {
        return patientARepository.findLastPatientA();
    }

    
    public PatientA findByIdPatientA(Long id) {
        Optional<PatientA> optionalPatientA = patientARepository.findById(id);
        return optionalPatientA.orElse(null);
    }

    
    public List<PatientA> findAllPatientsA() {
        return patientARepository.findAll();
    }

    
    public PatientA savePatientA(PatientA patientA) {
        return patientARepository.save(patientA);
    }

    
    public boolean deletePatientA(Long id) {
        if (patientARepository.existsById(id)) {
            patientARepository.deleteById(id);
            return true;
        }
        return false;
    }
}
