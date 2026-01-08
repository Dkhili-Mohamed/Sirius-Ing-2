package esiag.back.loaders;

import esiag.back.models.medical.Patient;
import esiag.back.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

@Order(1)
@Component
public class PatientDataLoader implements CommandLineRunner {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Ajout des patients à la base de données...");

        if(patientRepository.count() == 0) {


            Patient p1 = new Patient();
            p1.setNomPatient("Dupont");
            p1.setPrenomPatient("Jean");
            p1.setAgePatient(30);

            Patient p2 = new Patient();
            p2.setNomPatient("Martin");
            p2.setPrenomPatient("Claire");
            p2.setAgePatient(25);

            Patient p3 = new Patient();
            p3.setNomPatient("Durand");
            p3.setPrenomPatient("Luc");
            p3.setAgePatient(40);

            patientRepository.save(p1);
            System.out.println("Patient " + p1.getNomPatient() + " " + p1.getPrenomPatient() + " ajouté à la table Patients !");
            patientRepository.save(p2);
            System.out.println("Patient " + p2.getNomPatient() + " " + p2.getPrenomPatient() + " ajouté à la table Patients !");
            patientRepository.save(p3);
            System.out.println("Patient " + p3.getNomPatient() + " " + p3.getPrenomPatient() + " ajouté à la table Patients !");

        }

        else {
            System.out.println("Patients déjà ajoutés ou aucun nouveau patient à ajouter.");

        }


    }

}
