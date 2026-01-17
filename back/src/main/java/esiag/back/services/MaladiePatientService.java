package esiag.back.services;

import esiag.back.models.medical.DPI;
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

    public MaladiePatient findByIdMaladiePatient(Long idMaladiePatient) {
        Optional<MaladiePatient> optionalMaladiePatient = MaladiePatientRepository.findById(idMaladiePatient);
        return optionalMaladiePatient.orElse(null);
    }

    public List<MaladiePatient> findByPatientId(Long idPatient) {
        return MaladiePatientRepository.findByPatientIdPatient(idPatient);
    }


    public List<MaladiePatient> findAllMaladiePatients(){
        return MaladiePatientRepository.findAll();
    }

    public boolean deleteMaladiePatient(Long idMaladiePatient) {
        Optional<MaladiePatient> optionalMaladiePatient = MaladiePatientRepository.findById(idMaladiePatient);
        if (optionalMaladiePatient.isPresent()) {
            optionalMaladiePatient.ifPresent(MaladiePatient -> MaladiePatientRepository.delete(MaladiePatient));
            return true;
        }
        return false;
    }

    public MaladiePatient saveMaladiePatient(MaladiePatient maladiePatient) {return MaladiePatientRepository.save(maladiePatient);}
}