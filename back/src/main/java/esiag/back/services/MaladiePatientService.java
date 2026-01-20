package esiag.back.services;


import esiag.back.models.medical.MaladiePatient;
import esiag.back.repositories.MaladiePatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaladiePatientService {

    @Autowired
    private MaladiePatientRepository MaladiePatientRepository;

    @Autowired
    private PatientService patientService;

    public MaladiePatient findByIdMaladiePatient(Long idMaladiePatient) {
        Optional<MaladiePatient> optionalMaladiePatient = MaladiePatientRepository.findById(idMaladiePatient);
        return optionalMaladiePatient.orElse(null);
    }

    public MaladiePatient ajouterMaladie(MaladiePatient maladiePatient) {
        return MaladiePatientRepository.save(maladiePatient);
    }

    public List<MaladiePatient> findByPatientId(Long idPatient) {
        return MaladiePatientRepository.findByPatientIdPatient(idPatient);
    }

    public List<MaladiePatient> findAllMaladiePatients(){
        return MaladiePatientRepository.findAll();
    }

    public boolean deleteMaladiePatient(Long idMaladiePatient) {
        if (MaladiePatientRepository.existsById(idMaladiePatient)) {
            MaladiePatientRepository.deleteById(idMaladiePatient);
            return true;
        }
        return false;
    }

    public MaladiePatient updateMaladie(MaladiePatient maladiePatient) {
        return MaladiePatientRepository.save(maladiePatient);
    }

}