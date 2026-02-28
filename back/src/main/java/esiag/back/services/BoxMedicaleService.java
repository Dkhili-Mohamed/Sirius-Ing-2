package esiag.back.services;

import esiag.back.models.medical.BoxMedicale;
import esiag.back.models.medical.FileAttente;
import esiag.back.models.medical.NiveauUrgence;
import esiag.back.models.medical.Patient;
import esiag.back.repositories.BoxMedicaleRepository;
import esiag.back.repositories.FileAttenteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class BoxMedicaleService {

    private final FileAttenteRepository fileAttenteRepository;
    private final BoxMedicaleRepository boxMedicaleRepository;
    private final PatientService patientService;

    @Transactional
    public BoxMedicale mettrePatientDansBox() {
        try {
            if(fileAttenteRepository.count() == 0) {
             log.info("Pas de patients dans la file d'attente");
             return null;
            }

            log.info("Récupération des patients de la file d'attente");
            List<FileAttente> fileAttente = fileAttenteRepository.findAll();
            log.info("Récupération des patients triés de la file d'attente");
            List<FileAttente> fileAttenteTriee = patientService.trierParUrgence(fileAttente);


            log.info("Récupération du premier patient ({}) de la file d'attente triée",fileAttenteTriee.get(0));
            FileAttente premierPatient = fileAttenteTriee.get(0);

            if(premierPatient.getPatient() == null) {
                log.info("Pas de patients dans la box");
                return null;
            }

            if(boxMedicaleRepository.count() >= 3) {
                log.info("Plus de place dans les box");
                return null;
            }


            log.info("Insertion du premier patient ({} {}) dans la box",premierPatient.getPatient().getPrenomPatient(), premierPatient.getPatient().getNomPatient());
            BoxMedicale boxMedicale = new BoxMedicale();
            boxMedicale.setPatient(premierPatient.getPatient());


            log.info("Mise à jour des informations du patient {} {} dans la box", premierPatient.getPatient().getPrenomPatient(), premierPatient.getPatient().getNomPatient() );
            NiveauUrgence niveauUrgence = patientService.getNiveauUrgence(premierPatient.getPatient());
            int tempsSecondes = niveauUrgence.getTemps();

            boxMedicale.setTempsEstime(LocalTime.ofSecondOfDay(tempsSecondes));
            boxMedicale.setHeureEntree(LocalDateTime.now());

            log.info("Patient {} {} inséré dans la box avec succès",premierPatient.getPatient().getPrenomPatient(), premierPatient.getPatient().getNomPatient());
            BoxMedicale boxMedicaleSauvee = boxMedicaleRepository.save(boxMedicale);
            fileAttenteRepository.delete(premierPatient);

            return boxMedicaleSauvee;

        } catch (Exception e) {
            log.info("Erreur lors de l'insertion du patient dans la box");
            return null;
        }


    }

    @Transactional
    public List<BoxMedicale> getAllBoxMedicales() {
        return boxMedicaleRepository.findAll();
    }
//
//    @Transactional
//    public List<BoxMedicale> getBoxMedicaleLibre(){
//    }




}
