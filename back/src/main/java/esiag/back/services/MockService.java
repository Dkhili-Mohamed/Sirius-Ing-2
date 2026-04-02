package esiag.back.services;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import esiag.back.models.ambulance.Ambulance;
import esiag.back.models.ambulance.PatientA;
import esiag.back.repositories.ambulancerepository;
import esiag.back.repositories.patientArepository;

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

    public void MockData() {


        // je vide les deux tableaux a chaque fois que je lance le projet
        patientArepository.deleteAll();
        ambulancerepository.deleteAll();

        // Les listes de données
        List<String> adresses = List.of(
        "10 rue de Rivoli, 75001 Paris",
        "25 avenue des Champs-Élysées, 75008 Paris",
        "6 rue d'Alsace, 75010 Paris",
        "2 rue du Faubourg Saint-Honoré, 75008 Paris",
        "30 boulevard Haussmann, 75009 Paris",
        "14 avenue Foch, 75016 Paris",
        "33 avenue de la République, 75011 Paris",
        "5 rue Lafayette, 75009 Paris",
        "9 rue de la Paix, 75002 Paris",
        "11 avenue Jean Jaurès, 75019 Paris",
        "12 rue Oberkampf, 75011 Paris",
        "48 rue Saint-Denis, 75001 Paris",
        "7 rue de Turenne, 75003 Paris",
        "21 rue Mouffetard, 75005 Paris",
        "16 rue Monge, 75005 Paris",
        "52 boulevard Voltaire, 75011 Paris",
        "3 rue des Archives, 75004 Paris",
        "27 rue de Belleville, 75020 Paris",
        "44 rue Lecourbe, 75015 Paris",
        "8 rue de Charonne, 75011 Paris",
        "19 rue du Temple, 75004 Paris",
        "31 rue Vavin, 75006 Paris",
        "6 rue de Clichy, 75009 Paris",
        "15 rue de la Roquette, 75011 Paris",
        "22 rue Saint-Sabin, 75011 Paris",
        "13 rue de Rennes, 75006 Paris",
        "9 rue de Crimée, 75019 Paris",
        "28 rue de Vaugirard, 75006 Paris",
        "17 rue des Martyrs, 75009 Paris",
        "41 rue Ordener, 75018 Paris"
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

        List<String> prenoms = List.of(
                // Hommes
                "Jean","Pierre","Michel","Alain","Louis","Paul","Nicolas","Thomas","Lucas","Hugo",
                "Gabriel","Arthur","Raphaël","Jules","Adam","Léo","Nathan","Mathis","Enzo","Noah",
                "Maxime","Antoine","Julien","Romain","Alexandre","Victor","Baptiste","Clément","Quentin","Adrien",
                "Damien","Théo","Valentin","Sébastien","Florian","Guillaume","Hugo","Axel","Yanis","Mehdi",
                "Karim","Samir","Ibrahim","Youssef","Amine","Kylian","Dylan","Ethan","Noé","Maël",
                
                // Femmes
                "Marie","Camille","Léa","Chloé","Manon","Sarah","Julie","Laura","Inès","Anna",
                "Emma","Louise","Alice","Lina","Zoé","Jade","Clara","Eva","Rose","Ambre",
                "Lucie","Agathe","Jeanne","Elsa","Pauline","Amandine","Charlotte","Mathilde","Océane","Mélanie",
                "Elise","Margaux","Justine","Célia","Anaïs","Fanny","Hélène","Nina","Sophie","Juliette",
                "Aya","Maya","Leïla","Imane","Nour","Yasmine","Salomé","Louna","Capucine","Noémie"
        );

        //Je remplis les tableaux
        String prenom = prenoms.get(random.nextInt(prenoms.size()));
        String nom = noms.get(random.nextInt(noms.size()));
        PatientA patient = new PatientA();
        patient.setNompatientA(prenom + " " + nom);
        patient.setAdressepatientA(adresses.get(random.nextInt(adresses.size())));
        patient.setNumeropatientA(numerodetelephone());

        patientArepository.save(patient);

        System.out.println("Patient créé  " );
        

        
        for (int i = 0; i < 10.; i++) {
            Ambulance ambulance = new Ambulance();

            ambulance.setAdresseambulance(adresses.get(random.nextInt(adresses.size())));
            ambulance.setDisponibiliteambulance(random.nextBoolean());
            ambulance.setVitessemoyambulance(40.0 + random.nextInt(61));
            ambulance.setEquipementambulance(1.0 + random.nextInt(5));
            ambulance.setExperienceambulance(1.0 + random.nextInt(5));

            ambulancerepository.save(ambulance);


        }
        System.out.println("Ambulances créées  " );

    }
    // générer des numéros
    private String numerodetelephone() {
        return "06" + (10000000 + random.nextInt(89999999));
    }
}
