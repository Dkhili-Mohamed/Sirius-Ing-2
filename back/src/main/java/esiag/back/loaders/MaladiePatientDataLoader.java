package esiag.back.loaders;

import esiag.back.models.medical.Maladie;
import esiag.back.models.medical.MaladiePatient;
import esiag.back.models.medical.NiveauCCMU;
import esiag.back.models.medical.Patient;
import esiag.back.repositories.MaladiePatientRepository;
import esiag.back.repositories.MaladieRepository;
import esiag.back.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Order(4)
@Component
public class MaladiePatientDataLoader implements CommandLineRunner {

    @Autowired
    private MaladiePatientRepository maladiePatientRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MaladieRepository maladieRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Affectation des maladies aux patients en cours...");



        Optional<Patient> optPatient = patientRepository.findByNomPatient("Dupont");
        Optional<Maladie> optMaladie = maladieRepository.findById(1L);

        if(optMaladie.isPresent() && optPatient.isPresent()) {

            Patient p1 = optPatient.get();
            Maladie m1 = optMaladie.get();


            Optional<MaladiePatient> exist = maladiePatientRepository.findByPatientAndMaladie(p1, m1);


            if (exist.isEmpty()) {
                MaladiePatient mp1 = new MaladiePatient();
                mp1.setPatient(p1);
                mp1.setMaladie(m1);
                mp1.setNiveauCCMU(NiveauCCMU.NIVEAU_2);
                mp1.setDateDiagnostic(LocalDateTime.of(2026,1,8,10,30));


                maladiePatientRepository.save(mp1);

                System.out.println("Maladies affectées avec succès.");

            }

            else {
                System.out.println("Maladies déjà affectées au patient ou aucune nouvelle maladie à affecter.");

            }

        }

    }
}

