package esiag.back.services.fileattente;

import esiag.back.models.medical.*;
import esiag.back.repositories.fileattente.BoxMedicaleRepository;
import esiag.back.repositories.fileattente.FileAttenteRepository;
import esiag.back.repositories.fileattente.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class BoxMedicaleService {

    private final FileAttenteRepository fileAttenteRepository;
    private final BoxMedicaleRepository boxMedicaleRepository;
    private final PatientService patientService;
    private final PatientRepository patientRepository;


    @Scheduled(fixedRate = 10000)
    @Transactional
    public void mettrePatientDansBox() {
        try {
            log.info("*****Insertion des patients dans les box (si libres) toutes les 10 secondes*****");
            log.info("Récupération des patients de la file d'attente");
            List<FileAttente> fileAttente = fileAttenteRepository.findAll();
            log.info("Récupération des patients triés de la file d'attente");
            List<FileAttente> fileAttenteTriee = patientService.trierParUrgence(fileAttente);



            log.info("Récupération des box médicales");
            List<BoxMedicale> boxMedicale = boxMedicaleRepository.findAll();

            if(boxMedicaleRepository.countBoxMedicale() > 3 ) {
                log.info("Plus de box disponibles");
                return;
            }

            int patientsAMettre = 0;

            for (BoxMedicale bm : boxMedicale) {
                if(bm.getStatutBox() == StatutBox.LIBRE && patientsAMettre < fileAttenteTriee.size()) {
                    log.info("Récupération du premier patient ({}) de la file d'attente triée",fileAttenteTriee.get(0));
                    FileAttente premierPatient = fileAttenteTriee.get(patientsAMettre);

                    log.info("Insertion du premier patient ({} {}) dans la box",premierPatient.getPatient().getPrenomPatient(), premierPatient.getPatient().getNomPatient());
                    bm.setPatient(premierPatient.getPatient());
                    bm.setStatutBox(StatutBox.OCCUPEE);

                    log.info("Mise à jour des informations du patient {} {} dans la box en cours", premierPatient.getPatient().getPrenomPatient(), premierPatient.getPatient().getNomPatient() );
                    NiveauUrgence niveauUrgence = patientService.getNiveauUrgence(premierPatient.getPatient());

                    int tempsSecondes = niveauUrgence.getTempsAleatoire();

                    LocalDateTime maintenant = LocalDateTime.now();

                    bm.setTempsEstime(LocalTime.ofSecondOfDay(tempsSecondes));
                    bm.setHeureEntree(maintenant);
                    LocalDateTime libreA = maintenant.plusSeconds(tempsSecondes);
                    bm.setLibreA(libreA);

                    BoxMedicale boxMedicaleSauvee = boxMedicaleRepository.save(bm);
                    log.info("Patient {} {} inséré dans la box avec succès",premierPatient.getPatient().getPrenomPatient(), premierPatient.getPatient().getNomPatient());

                    patientsAMettre = patientsAMettre + 1;
                }



            }

            boxMedicaleRepository.saveAll(boxMedicale);


            for (int i = 0; i < patientsAMettre; i++) {
                FileAttente patientSupprime = fileAttenteTriee.get(i);
                fileAttenteRepository.deleteById(patientSupprime.getIdFileAttente());
            }



        } catch (Exception e) {
            log.error("Erreur lors de l'insertion du patient dans la box", e);

        }


    }


    @Transactional
    public boolean libererBox(long idBoxMedicale) {
        Optional<BoxMedicale> OptionalBoxMedicale = boxMedicaleRepository.findById(idBoxMedicale);

            if(OptionalBoxMedicale.isPresent()) {
                BoxMedicale bm = OptionalBoxMedicale.get();

                    if(bm.getPatient() != null) {
                        bm.getPatient().setStatutPatient(StatutPatient.CONSULTE);
                        patientRepository.saveAndFlush(bm.getPatient());
                    }

                    bm.setStatutBox(StatutBox.LIBRE);
                    bm.setPatient(null);
                    bm.setHeureEntree(null);
                    bm.setLibreA(null);
                    bm.setTempsEstime(null);
                    bm.setTempsRestant(0);





                    boxMedicaleRepository.saveAndFlush(bm);
                    log.info("Box {} liberee", bm.getNomBox());

                    return true;
                }

        return false;
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
