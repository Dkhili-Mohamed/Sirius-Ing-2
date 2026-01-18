package esiag.back.services;

import esiag.back.models.medical.DPI;
import esiag.back.models.medical.EtatSante;
import esiag.back.models.medical.MaladiePatient;
import esiag.back.models.medical.Patient;
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

    private void verifierEtatPatient(Long idPatient) {
        Patient patient = patientService.findByIdPatient(idPatient);
        if (patient != null) {
            boolean patientMalade = !MaladiePatientRepository.findByPatientIdPatient(idPatient).isEmpty();
            patient.setEtatSante(EtatSante.MALADE);
            patientService.save(patient);
        }
    }

    public MaladiePatient ajouterMaladie(MaladiePatient maladiePatient) {
        MaladiePatient resultat = MaladiePatientRepository.save(maladiePatient);
        verifierEtatPatient(maladiePatient.getPatient().getIdPatient());
        return resultat;
    }

    public List<MaladiePatient> findByPatientId(Long idPatient) {
        List<MaladiePatient> maladies = MaladiePatientRepository.findByPatientIdPatient(idPatient);

        Patient patient = patientService.findByIdPatient(idPatient);
        if (maladies.isEmpty()) {
            patient.setEtatSante(EtatSante.SAIN);
            patientService.save(patient);
        } else {
            patient.setEtatSante(EtatSante.MALADE);
            patientService.save(patient);
        }
        patientService.save(patient);

        return maladies;
    }


    public List<MaladiePatient> findAllMaladiePatients(){
        return MaladiePatientRepository.findAll();
    }

    public boolean deleteMaladiePatient(Long idMaladiePatient) {
        Optional<MaladiePatient> maladie = MaladiePatientRepository.findById(idMaladiePatient);

        if (maladie.isPresent()) {
            Long patientId = maladie.get().getPatient().getIdPatient();

            MaladiePatientRepository.deleteById(idMaladiePatient);

            List<MaladiePatient> maladiesRestantes = MaladiePatientRepository.findByPatientIdPatient(patientId);
            Patient patient = patientService.findByIdPatient(patientId);

            if (patient != null) {
                if (maladiesRestantes.isEmpty()) {
                    patient.setEtatSante(EtatSante.SAIN);
                } else {
                    patient.setEtatSante(EtatSante.MALADE);
                }
                patientService.save(patient);
            }

            return true;
        }
        return false;
    }

}