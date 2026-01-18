package esiag.back.services;

import esiag.back.models.medical.EtatSante;
import esiag.back.models.medical.FileAttente;
import esiag.back.models.medical.Patient;
import esiag.back.repositories.FileAttenteRepository;
import esiag.back.repositories.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;

@Service
@Transactional
@Slf4j
public class FileAttenteService {

    @Autowired
    private FileAttenteRepository fileAttenteRepository;

    @Autowired
    private PatientRepository patientRepository;

    public void ajouterPatientsMalades() {
        log.info("Recherche des patients malades a ajouter a la file d'attente");

        List<Patient> patientsMalades = patientRepository.findByEtatSante(EtatSante.MALADE);

        if(patientsMalades.isEmpty()) {
            log.info("Aucun patient malade");
            return;
        }

        log.info("{} patients malades trouves", patientsMalades.size());

        for (Patient patient : patientsMalades) {

            if (!fileAttenteRepository.existsByPatient(patient)) {
                FileAttente fileAttente = new FileAttente();
                fileAttente.setPatient(patient);
                fileAttente.setDate_entree(LocalDateTime.now());
                fileAttenteRepository.save(fileAttente);
                log.info("Patient ajoute à la file d'attente : [ID={}] {} {}", patient.getIdPatient(), patient.getPrenomPatient(), patient.getNomPatient());
            } else {
                log.info("Patient deja dans la file: [ID={}] {} {}", patient.getIdPatient(), patient.getPrenomPatient(), patient.getNomPatient());
            }
        }



    }
}
