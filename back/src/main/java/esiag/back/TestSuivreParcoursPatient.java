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
        System.out.println("-----------------------------------");
        for(ParcoursPatient p : parcoursPatient){
            System.out.println("Ordre : " +p.getOrdre()+ " ----- Libelle : "+ p.getLibelle());
        }
        System.out.println("-----------------------------------");
        System.out.println("Suivi du parcours du patient :");
        // --- SIMULATION DU BOUTON "COMMENCER" (Ordre 0) ---
        System.out.println("Bouton COMMENCER cliqué...");
        Long idDepart = 6L; // L'entrée de l'hôpital
        List<Espace> cheminVersUn = cheminService.nextActeMedical(6L, 0, idDepart);
        for (ParcoursPatient acte : parcoursPatient) {

            System.out.println("Acte médical à réaliser :");
            System.out.println(acte);

            System.out.println("--------------------------------------------------");
            System.out.println("-------DEBUT ETAPE Numéro : " + acte.getOrdre() + "------------------------");
            System.out.println("---------------------------------------------------");
            // Exemple d'utilisation de CheminService pour trouver le chemin entre deux
            // espaces
            // ID de l'espace de départ (à adapter)
            List<Espace> chemin = cheminService.nextActeMedical(acte.getIdParcours(), acte.getOrdre(), idDepart);
            System.out.println("-------------------------------------------------");
            System.out.println("--------------------------------------------------");
            if (chemin == null) {
                System.out.println("-----------------PARCOURS TERMINE--------------------------------");
                System.out.println("--------------------------------------------------");
            } else {
                for (Espace espace : chemin) {
                    System.out.println("Espace à traverser : " + espace.getNumeroEspace());
                }
                System.out.println("--------------------------------------------------");
                System.out.println("-------FIN ETAPE Numéro : " + acte.getOrdre() + "------------------------");
                System.out.println("---------------------------------------------------");
                idDepart = chemin.get(chemin.size() - 1).getIdEspace();
            }

            
        }
    }
}
