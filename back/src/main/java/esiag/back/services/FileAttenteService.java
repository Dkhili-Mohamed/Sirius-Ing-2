package esiag.back.services;

import esiag.back.models.medical.EtatSante;
import esiag.back.models.medical.Patient;
import esiag.back.models.medical.MaladiePatient;
import esiag.back.models.medical.NiveauCCMU;
import esiag.back.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class FileAttenteService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getPatientsTriees() {
        List<Patient> allPatients = patientRepository.findAll();
        List<Patient> malades = new ArrayList<>();

        for (Patient p : allPatients) {
            if (p.getEtatSante() == EtatSante.MALADE) {
                malades.add(p);
            }
        }

        Collections.sort(malades, new Comparator<Patient>() {
            @Override
            public int compare(Patient p1, Patient p2) {
                NiveauCCMU n1 = NiveauCCMU.NIVEAU_1;
                NiveauCCMU n2 = NiveauCCMU.NIVEAU_1;

                for (MaladiePatient mp : p1.getMaladies()) {
                    if (mp.getNiveauCCMU().ordinal() > n1.ordinal()) {
                        n1 = mp.getNiveauCCMU();
                    }
                }

                for (MaladiePatient mp : p2.getMaladies()) {
                    if (mp.getNiveauCCMU().ordinal() > n2.ordinal()) {
                        n2 = mp.getNiveauCCMU();
                    }
                }

                if (n2.ordinal() != n1.ordinal()) {
                    return Integer.compare(n2.ordinal(), n1.ordinal());
                }

                if (!p2.getAgePatient().equals(p1.getAgePatient())) {
                    return Integer.compare(p2.getAgePatient(), p1.getAgePatient());
                }

                LocalDateTime d1 = p1.getMaladies().isEmpty() ? LocalDateTime.MIN : p1.getMaladies().get(0).getDateDiagnostic();
                LocalDateTime d2 = p2.getMaladies().isEmpty() ? LocalDateTime.MIN : p2.getMaladies().get(0).getDateDiagnostic();

                return d1.compareTo(d2);
            }
        });

        return malades;
    }
}
