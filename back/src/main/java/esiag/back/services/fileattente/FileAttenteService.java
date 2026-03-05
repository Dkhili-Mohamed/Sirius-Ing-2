package esiag.back.services.fileattente;

import esiag.back.models.dto.FileAttenteDTO;
import esiag.back.models.medical.BoxMedicale;
import esiag.back.models.medical.FileAttente;
import esiag.back.models.medical.Patient;
import esiag.back.models.medical.StatutBox;
import esiag.back.repositories.fileattente.BoxMedicaleRepository;
import esiag.back.repositories.fileattente.FileAttenteRepository;
import esiag.back.repositories.fileattente.PatientRepository;
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
    private final PatientService patientService;
    private final BoxMedicaleRepository boxMedicaleRepository;


    @Transactional
    public List<FileAttente> getFileAttenteTriee() {
        log.info("Demarrage du tri de la file d'attente");

        log.info("-- 1. Recuperation des patients depuis la base de donnees --");
        List<Patient> patients = patientRepository.findAll();
        log.info("Nombre de patients a trier : {}", patients.size());

        log.info("-- 2. Lancement de l'algorithme de tri par niveau d'urgence --");
        List<FileAttente> fileAttenteRecuperee = patientService.trierParUrgence(fileAttenteRepository.findAll());
        List<FileAttente> fileAttenteTriee = patientService.trierParUrgence(fileAttenteRecuperee);

        log.info("Tri termine. {} patients tries par niveau d'urgence", fileAttenteTriee.size());

        log.info("- Transfert des {} patients tries pour la mise a jour -", fileAttenteTriee.size());
        if(!fileAttenteTriee.isEmpty()) {
            log.info("Premier patient trie: {}", fileAttenteTriee.get(0).getPatient());
            log.info("-> Dernier patient trie: {}", fileAttenteTriee.get(fileAttenteTriee.size()-1).getPatient());


        }


        return mettreAJourFileAttente(fileAttenteTriee);
    }

    @Transactional
    public List<FileAttenteDTO> getFileAttenteAvecScores() {
        List<FileAttente> fileAttente = getFileAttenteTriee();
        List<FileAttenteDTO> result = new ArrayList<>();

        for (FileAttente fa : fileAttente) {
            FileAttenteDTO dto = new FileAttenteDTO(fa, patientService, 0);
            result.add(dto);
        }

        return result;
    }

    @Transactional
    public List<FileAttenteDTO> calculerTempsAttenteEstime() {
        List<FileAttente> fileAttentes = getFileAttenteTriee();

        if(fileAttentes.isEmpty()) {
            log.info("Pas de patient dans la file d'attente");


            return  new ArrayList<>();
        }

        List<BoxMedicale> boxMedicales = boxMedicaleRepository.findAll();


        List<FileAttenteDTO> fileAttenteDTOS = new ArrayList<>();


        int temps_minimum_attente = Integer.MAX_VALUE;
        for (BoxMedicale bm : boxMedicales) {
            if(bm.getStatutBox() == StatutBox.OCCUPEE) {

                int temps_restant = bm.getTempsRestant();
                if (temps_restant < temps_minimum_attente) {
                    temps_minimum_attente = temps_restant;
                }

            }else if (bm.getStatutBox() == StatutBox.LIBRE) {
                temps_minimum_attente = 0;
            }


        }


        int temps_attente_estime = temps_minimum_attente;
        for  (int i=0; i<fileAttentes.size(); i++) {

            Patient patient = fileAttentes.get(i).getPatient();
            int temps_consultation_estime = patientService.getNiveauUrgence(patient).getTempsMoyen();


            FileAttenteDTO fileAttenteDTO = new FileAttenteDTO(fileAttentes.get(i), patientService, temps_attente_estime);
            fileAttenteDTOS.add(fileAttenteDTO);

            temps_attente_estime = temps_attente_estime + temps_consultation_estime;


        }

        return fileAttenteDTOS;

    }


    @Transactional
    public List<FileAttente> mettreAJourFileAttente(List<FileAttente> fileAttenteMAJ) {
        log.info("-- 3. Mise a jour de la file d'attente avec {} patients tries --", fileAttenteMAJ.size());

        try {
            if (fileAttenteRepository.count() > 0) {
                log.info("Suppression de l'ancienne file d'attente");
                fileAttenteRepository.deleteAllInBatch();
            }

            log.info("Création des nouvelles entrees de file d'attente");
            List<FileAttente> fileAttente = new ArrayList<>();
            LocalDateTime maintenant = LocalDateTime.now();

            for (int position = 0; position < fileAttenteMAJ.size(); position++) {
                FileAttente fileAttenteTriee = fileAttenteMAJ.get(position);
                FileAttente entree = new FileAttente();
                entree.setPatient(fileAttenteTriee.getPatient());
                entree.setDateEntree(fileAttenteTriee.getDateEntree());
                entree.setRang(position + 1);
                fileAttente.add(entree);
            }

            return fileAttenteRepository.saveAll(fileAttente);

        } catch (Exception e) {
            log.error("Erreur lors de la mise a jour de la file d'attente", e);
            throw new RuntimeException("Erreur lors de la mise a jour de la file d'attente", e);
        }

    }

}