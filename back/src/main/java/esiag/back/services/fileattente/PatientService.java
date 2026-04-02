package esiag.back.services.fileattente;

import esiag.back.models.medical.NiveauUrgence;
import esiag.back.models.medical.Patient;
import esiag.back.repositories.fileattente.PatientRepository;

import esiag.back.models.medical.*;
import esiag.back.repositories.fileattente.BoxMedicaleRepository;
import esiag.back.repositories.fileattente.FileAttenteRepository;
import esiag.back.repositories.fileattente.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;


import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private FileAttenteRepository fileAttenteRepository;
    @Autowired
    private BoxMedicaleRepository boxMedicaleRepository;
    @Autowired
    private SymptomeService symptomeService;

    public Patient findByIdPatient(Long idPatient) {
        Optional<Patient> optionalPatient = patientRepository.findById(idPatient);
        return optionalPatient.orElse(null);
    }


    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }

    //Sera peut-être utilisé ultérieurement
//    public boolean deletePatient(Long idPatient) {
//        Optional<Patient> optionalPatient = patientRepository.findById(idPatient);
//        if (optionalPatient.isPresent()) {
//            optionalPatient.ifPresent(patient -> patientRepository.delete(patient));
//            return true;
//        }
//        return false;
//    }

    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }


//    private int calculerPointsSymptome(String symptome) {
//        switch (symptome) {
//            case "fievre_elevee":
//            case "fievreElevee":
//            case "fievre Elevee":
//                return 3;
//            case "douleur_intense":
//            case "douleurIntense":
//                return 4;
//            case "douleur_thoracique":
//            case "douleurThoracique":
//                return 5;
//            case "difficulte_respiratoire":
//            case "difficultesRespiratoires":
//            case "difficultes Respiratoires":
//                return 5;
//            case "perte_connaissance":
//            case "perteConnaissance":
//            case "perte Conaissance":
//                return 5;
//            case "hemorragie":
//            case "saignementAbondant":
//            case "saignement Abondant":
//                return 5;
//            case "douleur_moderee":
//            case "douleurModeree":
//                return 2;
//            case "nausee":
//            case "vomissementsPersistants":
//                return 1;
//            case "fatigue":
//            case "fatigueExtreme":
//                return 1;
//            case "confusion":
//                return 2;
//            case "frissons":
//                return 1;
//            case "touxSevere":
//            case "toux Severe":
//            case "toux_severe":
//                return 2;
//            case "malaiseGeneral":
//            case "malaise General":
//                return 1;
//            case "vertigesIntenses":
//            case "vertiges Intenses":
//                return 2;
//            case "mauxTeteSeveres":
//            case "maux Tete Severes":
//                return 1;
//            case "visionTroublee":
//            case "vision Troublee":
//                return 2;
//            case "difficultesParole":
//            case "difficultes Parole":
//                return 3;
//            case "faiblesseBrasJambes":
//            case "faiblesse Bras Jambes":
//                return 2;
//            case "engourdissementFace":
//            case "engourdissement Face":
//                return 3;
//            default:
//                return 0;
//        }
//    }


    private int calculerPointsAge(Integer agePatient) {
        if (agePatient == 1) return 4;
        if (agePatient <= 5) return 3;
        if (agePatient <= 12) return 2;
        if (agePatient <= 17) return 1;
        if (agePatient >= 65) return 3;
        return 0;
    }

//    public int calculerScoreUrgence(Patient patient) {
//        int score = 0;
//
//        if (patient.getSymptomes() != null && !patient.getSymptomes().isEmpty()) {
//            for (String symptome : patient.getSymptomes()) {
//                score += calculerPointsSymptome(symptome.trim().toLowerCase());
//            }
//        }
//
//        score += calculerPointsAge(patient.getAgePatient());
//        return score;
//    }

    public int calculerScore(Patient patient) {
        int score = 0;

        List<Symptome> symptomes = symptomeService.findSymptomeByIdPatient(patient.getIdPatient());

        if(!symptomes.isEmpty()) {
            for (Symptome  symptome : symptomes) {
                score = score + symptome.getScore();
            }

        }
        score = score + calculerPointsAge(patient.getAgePatient());
        return score;
    }

    @Transient
    public NiveauUrgence getNiveauUrgence(Patient patient) {
        int score = calculerScore(patient);
        if (score >= 8) return NiveauUrgence.URGENT;
        if (score >= 4) return NiveauUrgence.INTERMEDIAIRE;
        return NiveauUrgence.NON_URGENT;
    }

    public List<FileAttente> trierParUrgence(List<FileAttente> fileAttente) {
        if (fileAttente == null)
            return new ArrayList<>();

        Collections.sort(fileAttente, new Comparator<FileAttente>() {
            //@Override
            public int compare(FileAttente f1, FileAttente f2) {

                Patient p1 = f1.getPatient();
                Patient p2 = f2.getPatient();

                NiveauUrgence n1 = getNiveauUrgence(p1);
                NiveauUrgence n2 = getNiveauUrgence(p2);

                if (n1 != n2) {
                    return Integer.compare(n2.ordinal(), n1.ordinal());
                }

                int s1 = calculerScore(p1);
                int s2 = calculerScore(p2);

                if (s1 != s2) {
                    return Integer.compare(s2, s1);
                }

                LocalDateTime d1 = f1.getDateEntree();
                LocalDateTime d2 = f2.getDateEntree();

                if (d1 == null && d2 == null)
                    return 0;
                if (d1 == null)
                    return 1;
                if (d2 == null)
                    return -1;

                return d1.compareTo(d2);
            }
        });



        return fileAttente;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void mettrePatientDansFile() {
        log.info("*****Insertion des patients non consultes dans la file chaque minute*****");

        log.info("Recuperation des patients");
        List<Patient> patients = patientRepository.findAll();
        log.info("Recuperation des patients triés de la file");
        List<FileAttente> fileAttente = fileAttenteRepository.findAll();
        log.info("Recuperation des box");
        List<BoxMedicale> boxMedicales = boxMedicaleRepository.findAll();


        for(Patient p : patients) {
            if(p.getStatutPatient() !=null && p.getStatutPatient() == StatutPatient.CONSULTE) {
                continue;
            }



            boolean patientDejaDansFile = false;
            boolean patientDejaDansBox = false;
            for (FileAttente fa : fileAttente) {
                if (fa.getPatient().getIdPatient().equals(p.getIdPatient())) {
                    patientDejaDansFile = true;
                }
            }

            for (BoxMedicale bm : boxMedicales) {
                if (bm.getPatient() != null && bm.getPatient().getIdPatient().equals(p.getIdPatient())) {
                    patientDejaDansBox = true;
                }
            }

            if (!patientDejaDansBox && !patientDejaDansFile) {
                FileAttente fa = new FileAttente();
                fa.setPatient(p);
                fa.setDateEntree(LocalDateTime.now());
                fileAttente.add(fa);
                fa.setRang(0);

                if(fa.getPatient() == null ||fa.getPatient().getIdPatient() == null) {
                    log.info("Impossible d'ajouter un patient null dans la file");
                    continue;
                }

                fileAttenteRepository.save(fa);
            }

            log.info("Patient {} {} inséré dans la box", p.getNomPatient(), p.getPrenomPatient());

        }


    }

}