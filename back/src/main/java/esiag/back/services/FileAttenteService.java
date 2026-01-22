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

        log.info("-- 1. Recuperation des patients depuis la base de donnees --");
        List<Patient> patients = patientRepository.findAll();
        log.info("Nombre de patients a trier : {}", patients.size());

        log.info("-- 2. Lancement de l'algorithme de tri par niveau d'urgence --");
        List<Patient> patientsTries = Patient.trierParUrgence(patients);
        log.info("Tri termine. {} patients tries par niveau d'urgence", patientsTries.size());

        log.info("- Transfert des {} patients tries vers la mise a jour -", patientsTries.size());
        log.info("Premier patient trie: {} (Score: {} - Niveau: {})",
                patientsTries.get(0).getNomPatient() + " " + patientsTries.get(0).getPrenomPatient(),
                patientsTries.get(0).getScoreUrgence(),
                patientsTries.get(0).getNiveauUrgence());
        log.info("-> Dernier patient trie: {} (Score: {} - Niveau: {})",
                patientsTries.get(patientsTries.size()-1).getNomPatient() + " " + patientsTries.get(patientsTries.size()-1).getPrenomPatient(),
                patientsTries.get(patientsTries.size()-1).getScoreUrgence(),
                patientsTries.get(patientsTries.size()-1).getNiveauUrgence());



        return mettreAJourFileAttente(patientsTries);
    }

    @Transactional
    public List<FileAttente> mettreAJourFileAttente(List<Patient> patients) {
        log.info("-- 3. Mise a jour de la file d'attente avec {} patients tries --", patients.size());

        try {
            if (fileAttenteRepository.count() > 0) {
                log.info("Suppression de l'ancienne file d'attente");
                fileAttenteRepository.deleteAllInBatch();
            }

            log.info("Création des nouvelles entrees de file d'attente");
            List<FileAttente> fileAttente = new ArrayList<>();
            LocalDateTime maintenant = LocalDateTime.now();

            for (int position = 0; position < patients.size(); position++) {
                FileAttente entree = new FileAttente();
                entree.setPatient(patients.get(position));
                entree.setDateEntree(maintenant.plusSeconds(position));
                entree.setRang(position + 1);
                fileAttente.add(entree);


                Patient p = patients.get(position);
                log.info("   Position {}: {} - Score: {} - Niveau: {}",
                        position + 1,
                        p.getNomPatient() + " " + p.getPrenomPatient(),
                        p.getScoreUrgence(),
                        p.getNiveauUrgence());
            }



            return fileAttenteRepository.saveAll(fileAttente);

        } catch (Exception e) {
            log.error("Erreur lors de la mise a jour de la file d'attente", e);
            throw new RuntimeException("Erreur lors de la mise a jour de la file d'attente", e);
        }
    }

}