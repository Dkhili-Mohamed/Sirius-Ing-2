package esiag.back.services;

import esiag.back.models.medical.FileAttente;
import esiag.back.models.medical.Patient;
import esiag.back.repositories.FileAttenteRepository;
import esiag.back.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileAttenteService {

    private final FileAttenteRepository fileAttenteRepository;
    private final PatientRepository patientRepository;

    public List<FileAttente> getFileAttenteTriee() {
        log.info("Demarrage du tri de la file d'attente");

        List<Patient> patients = patientRepository.findAll();
        log.info("Nombre de patients a trier : {}", patients.size());

        for (Patient patient : patients) {
            patient.calculerEtMettreAJourScore();
        }
        patientRepository.saveAll(patients);

        List<Patient> patientsTries = Patient.trierParUrgence(patients);
        log.info("Tri termine. {} patients triés par niveau d'urgence", patientsTries.size());

        return mettreAJourFileAttente(patientsTries);
    }

    @Transactional
    public List<FileAttente> mettreAJourFileAttente(List<Patient> patients) {
        log.info("Mise a jour de la file d'attente avec {} patients", patients.size());

        try {
            if (fileAttenteRepository.count() > 0) {
                fileAttenteRepository.deleteAllInBatch();
            }

            List<FileAttente> fileAttente = new ArrayList<>();
            LocalDateTime maintenant = LocalDateTime.now();

            for (int position = 0; position < patients.size(); position++) {
                FileAttente entree = new FileAttente();
                entree.setPatient(patients.get(position));
                entree.setDateEntree(maintenant.plusSeconds(position));
                entree.setRang(position + 1);
                fileAttente.add(entree);
            }

            return fileAttenteRepository.saveAll(fileAttente);

        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour de la file d'attente", e);
            throw new RuntimeException("Erreur lors de la mise à jour de la file d'attente", e);
        }
    }

}