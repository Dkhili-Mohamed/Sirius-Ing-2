package esiag.back.services;

import esiag.back.models.medical.Patient;
import esiag.back.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient findByIdPatient(Long idPatient) {
        Optional<Patient> optionalPatient = patientRepository.findById(idPatient);
        return optionalPatient.orElse(null);
    }


    public List<Patient> findAllPatients(){
        return patientRepository.findAll();
    }

    public boolean deletePatient(Long idPatient) {
        Optional<Patient> optionalPatient = patientRepository.findById(idPatient);
        if (optionalPatient.isPresent()) {
            optionalPatient.ifPresent(patient -> patientRepository.delete(patient));
            return true;
        }
        return false;
    }

    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }
}
