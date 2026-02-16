package esiag.back.services;

import esiag.back.models.dto.ParcoursPatient;
import esiag.back.models.medical.NiveauUrgence;
import esiag.back.models.medical.Patient;
import esiag.back.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ParcoursService parcoursService;

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


    private int calculerPointsSymptome(String symptome) {
        switch (symptome) {
            case "fievre_elevee":
            case "fievreElevee":
                return 3;
            case "douleur_intense":
            case "douleurIntense":
                return 4;
            case "douleur_thoracique":
            case "douleurThoracique":
                return 5;
            case "difficulte_respiratoire":
            case "difficultesRespiratoires":
                return 5;
            case "perte_connaissance":
            case "perteConnaissance":
                return 5;
            case "hemorragie":
            case "saignementAbondant":
                return 5;
            case "douleur_moderee":
            case "douleurModeree":
                return 2;
            case "nausee":
            case "vomissementsPersistants":
                return 1;
            case "fatigue":
            case "fatigueExtreme":
                return 1;
            case "confusion":
                return 2;
            case "frissons":
                return 1;
            case "touxSevere":
                return 2;
            case "malaiseGeneral":
                return 1;
            case "vertigesIntenses":
                return 2;
            case "mauxTeteSeveres":
                return 1;
            case "visionTroublee":
                return 2;
            case "difficultesParole":
                return 3;
            case "faiblesseBrasJambes":
                return 2;
            case "engourdissementFace":
                return 3;
            default:
                return 0;
        }
    }


    private int calculerPointsAge(Integer agePatient) {
        if (agePatient == null) {
            return 0; 
        }
        if (agePatient == 1) return 4;
        if (agePatient <= 5) return 3;
        if (agePatient <= 12) return 2;
        if (agePatient <= 17) return 1;
        if (agePatient >= 65) return 3;
        return 0;
    }

    public int calculerScoreUrgence(Patient patient) {
        int score = 0;

        if (patient.getSymptomes() != null && !patient.getSymptomes().isEmpty()) {
            for (String symptome : patient.getSymptomes()) {
                score += calculerPointsSymptome(symptome.trim().toLowerCase());
            }
        }

        score += calculerPointsAge(patient.getAgePatient());
        return score;
    }

    @Transient
    public NiveauUrgence getNiveauUrgence(Patient patient) {
        int score = calculerScoreUrgence(patient);
        if (score >= 8) return NiveauUrgence.URGENT;
        if (score >= 4) return NiveauUrgence.INTERMEDIAIRE;
        return NiveauUrgence.NON_URGENT;
    }

    public List<Patient> trierParUrgence(List<Patient> patients) {
        if (patients == null)
            return new ArrayList<>();

        Collections.sort(patients, new Comparator<Patient>() {
            @Override
            public int compare(Patient p1, Patient p2) {

                NiveauUrgence n1 = getNiveauUrgence(p1);
                NiveauUrgence n2 = getNiveauUrgence(p2);

                if (n1 != n2) {
                    return Integer.compare(n2.ordinal(), n1.ordinal());
                }

                int s1 = calculerScoreUrgence(p1);
                int s2 = calculerScoreUrgence(p2);

                if (s1 != s2) {
                    return Integer.compare(s2, s1);
                }

                LocalDateTime d1 = p1.getDateArrivee();
                LocalDateTime d2 = p2.getDateArrivee();

                if (d1 == null && d2 == null)
                    return 0;
                if (d1 == null)
                    return 1;
                if (d2 == null)
                    return -1;

                return d1.compareTo(d2);
            }
        });

        return patients;
    }


    public List<ParcoursPatient> getParcoursPatientsByEmail(String email) {

        List<ParcoursPatient> parcoursPatients = new ArrayList<>();
        Optional<Patient> optionalPatient = patientRepository.findByEmail(email);

        if(optionalPatient.isPresent()){


            Patient patient = optionalPatient.get();

            parcoursPatients = parcoursService.findParcoursPatientById(patient.getIdPatient());


        }
        return parcoursPatients;

    }

    

}