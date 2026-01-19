package esiag.back;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import esiag.back.models.architecture.Espace;
import esiag.back.models.dto.ParcoursPatient;
import esiag.back.models.medical.Parcours;
import esiag.back.services.CheminService;
import esiag.back.services.ParcoursService;

@Component
public class TestSuivreParcoursPatient implements CommandLineRunner {
     
    @Autowired
    private ParcoursService parcoursService;
    @Autowired
    private CheminService cheminService;

    public TestSuivreParcoursPatient(ParcoursService parcoursService, CheminService cheminService) {
        this.parcoursService = parcoursService;
        this.cheminService = cheminService;
    }   

    @Override
    public void run(String... args) throws Exception {
        // Code de test pour suivre le parcours d'un patient

        Long idPatient = 3L;
        
        List<ParcoursPatient> parcoursPatient = parcoursService.findParcoursPatientById(idPatient);

        System.out.println("Parcours du patient avec ID " + idPatient + " :");
        System.out.println(parcoursPatient);  
        System.out.println("-----------------------------------");
        System.out.println("Suivi du parcours du patient :");
        Long idDepart = 6L; // ID de l'espace de départ (à adapter)
        for (ParcoursPatient acte : parcoursPatient) {

            System.out.println("Acte médical à réaliser :");
            System.out.println(acte);

            
            // Exemple d'utilisation de CheminService pour trouver le chemin entre deux espaces
         // ID de l'espace de départ (à adapter)
            List<Espace> chemin = cheminService.nextActeMedical(acte.getIdTypeActeMedical(), acte.getIdActeMedical(), idDepart);
            System.out.println("-------------------------------------------------");
            System.out.println("--------------------------------------------------");
            for (Espace espace : chemin) {
                System.out.println("Espace à traverser : " + espace);
            }
            System.out.println("--------------------------------------------------");
            System.out.println("-------FIN PREMIERE ETAPE " + acte.getOrdre() + "------------------------");
            System.out.println("---------------------------------------------------");

            idDepart = chemin.get(chemin.size() - 1).getIdEspace();
        }
    }


    
}
