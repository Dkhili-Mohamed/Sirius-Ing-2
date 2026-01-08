package esiag.back.loaders;

import esiag.back.models.medical.DPI;
import esiag.back.models.medical.Patient;
import esiag.back.repositories.DPIRepository;
import esiag.back.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Order(3)
@Component
public class DPIDataLoader implements CommandLineRunner {

    @Autowired
    private DPIRepository dpiRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Modification de DPI en cours...");

        Optional<Patient> optP1 = patientRepository.findByNomPatient("Dupont");

            if (optP1.isPresent()) {
                Patient p1 = optP1.get();
                DPI dpi1 = new DPI();
                dpi1.setPatient(p1);
                dpi1.setAntecedent("Allergie aux pénicillines");
                dpi1.setTraitement("Surveillance régulière");

                dpiRepository.save(dpi1);

                System.out.println("DPI modifié avec succès.");

            }



        Optional<Patient> optP2 = patientRepository.findByNomPatient("Martin");
            if (optP2.isPresent()) {
                Patient p2 = optP2.get();
                DPI dpi2 = new DPI();
                dpi2.setPatient(p2);
                dpi2.setAntecedent("Allergie aux arachides");
                dpi2.setTraitement("Surveiller la composition des produits");

                dpiRepository.save(dpi2);

                System.out.println("DPI modifié avec succès.");
            }

            }




    }

