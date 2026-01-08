package esiag.back.loaders;

import esiag.back.models.medical.Maladie;
import esiag.back.repositories.MaladieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Order(2)
@Component
public class MaladieDataLoader implements CommandLineRunner {

    @Autowired
    private MaladieRepository maladieRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Ajout des maladies à la base de données...");


        if(maladieRepository.count() == 0) {

            Maladie m1 = new Maladie();
            m1.setNomMaladie("Angine");
            m1.setDescriptionMaladie("Depuis deux jours");
            maladieRepository.save(m1);

            Maladie m2 = new Maladie();
            m2.setNomMaladie("Grippe");
            m2.setDescriptionMaladie("Fièvre de plus de 37 degrés");
            maladieRepository.save(m2);

            System.out.println("Nouvelles maladies ajoutées avec succès.");
        }

        else {
            System.out.println("Maladies déjà ajoutées ou aucune nouvelle maladie à ajouter.");

        }

    }

}
