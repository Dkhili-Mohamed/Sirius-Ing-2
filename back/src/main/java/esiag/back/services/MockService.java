package esiag.back.services;

import esiag.back.models.ambulance.Ambulance;
import esiag.back.models.ambulance.PatientA;
import esiag.back.repositories.ambulancerepository;
import esiag.back.repositories.patientArepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

@Service
public class MockService {

    private final ambulancerepository ambulancerepository;
    private final patientArepository patientArepository;

    private final Random random = new Random();

    public MockService(ambulancerepository ambulancerepository,
                           patientArepository patientArepository) {
        this.ambulancerepository = ambulancerepository;
        this.patientArepository = patientArepository;
    }

    @PostConstruct
    public void MockData() {


        // je vide les deux tableaux a chaque fois que je lance le projet
        patientArepository.deleteAll();
        ambulancerepository.deleteAll();

        // Les listes de données
        List<String> adresses = List.of(
                "10 rue de Rivoli, 75001 Paris",
                "25 avenue des Champs-Élysées, 75008 Paris",
                "5 place Bellecour, 69002 Lyon",
                "12 rue de la République, 13001 Marseille",
                "18 boulevard Victor Hugo, 06000 Nice",
                "7 rue Nationale, 59000 Lille",
                "3 place du Capitole, 31000 Toulouse",
                "8 rue Sainte-Catherine, 33000 Bordeaux",
                "20 rue de Strasbourg, 67000 Strasbourg",
                "9 rue Gambetta, 44000 Nantes",
                "15 avenue Jean Médecin, 06000 Nice",
                "6 rue d'Alsace, 75010 Paris",
                "2 rue du Faubourg Saint-Honoré, 75008 Paris",
                "30 boulevard Haussmann, 75009 Paris",
                "1 place de la Comédie, 34000 Montpellier",
                "14 avenue Foch, 75016 Paris",
                "21 rue de Metz, 31000 Toulouse",
                "11 place Kléber, 67000 Strasbourg",
                "27 boulevard Carnot, 59800 Lille",
                "4 rue Victor Hugo, 69002 Lyon",
                "19 rue Nationale, 59000 Lille",
                "33 avenue de la République, 75011 Paris",
                "5 rue Lafayette, 75009 Paris",
                "8 place de la Bourse, 33000 Bordeaux",
                "12 boulevard Longchamp, 13001 Marseille",
                "6 avenue de la Liberté, 67000 Strasbourg",
                "17 rue Thiers, 06000 Nice",
                "22 rue des Capucins, 69001 Lyon",
                "1 avenue de Verdun, 26000 Valence",
                "9 rue de la Paix, 75002 Paris",
                "3 place Masséna, 06000 Nice",
                "16 boulevard de la Liberté, 35000 Rennes",
                "5 rue de Siam, 29200 Brest",
                "10 place Royale, 44000 Nantes",
                "2 avenue Alsace-Lorraine, 38000 Grenoble",
                "18 rue de la Barre, 59000 Lille",
                "14 boulevard Victor Hugo, 34000 Montpellier",
                "7 place du Ralliement, 49000 Angers",
                "11 avenue Jean Jaurès, 75019 Paris",
                "4 rue Nationale, 59800 Lille"
        );

        List<String> noms = List.of(
                "Dupont", "Martin", "Bernard", "Thomas", "Petit",
                "Robert", "Richard", "Durand", "Moreau", "Laurent",
                "Simon", "Michel", "Lefebvre", "Leroy", "Roux",
                "David", "Bertrand", "Morel", "Fournier", "Girard",
                "Bonnet", "Dupuis", "Lambert", "Fontaine", "Rousseau",
                "Vincent", "Muller", "Lefevre", "Faure", "Andre",
                "Mercier", "Blanc", "Guerin", "Boyer", "Garnier",
                "Chevalier", "Francois", "Legrand", "Gauthier", "Perrin",
                "Robin", "Clement", "Morin", "Nicolas", "Henry",
                "Roussel", "Mathieu", "Gautier", "Masson", "Marchand"
        );

        //Je remplis les tableaux
        PatientA patient = new PatientA();
        patient.setNompatientA(noms.get(random.nextInt(noms.size())));
        patient.setAdressepatientA(adresses.get(random.nextInt(adresses.size())));
        patient.setNumeropatientA(numerodetelephone());

        patientArepository.save(patient);

        System.out.println("Patient créé  " );

        
        for (int i = 0; i < 20; i++) {
            Ambulance ambulance = new Ambulance();

            ambulance.setAdresseambulance(adresses.get(random.nextInt(adresses.size())));
            ambulance.setDisponibiliteambulance(random.nextBoolean());
            ambulance.setVitessemoyambulance(40.0 + random.nextInt(61));
            ambulance.setEquipementambulance(1.0 + random.nextInt(10));
            ambulance.setExperienceambulance(1.0 + random.nextInt(10));

            ambulancerepository.save(ambulance);


        }
        System.out.println("Ambulances créées  " );

    }
    // générer des numéros
    private String numerodetelephone() {
        return "06" + (10000000 + random.nextInt(89999999));
    }
}
