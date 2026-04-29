package esiag.back.services.parcours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import esiag.back.models.architecture.Connexion;
import esiag.back.models.architecture.Espace;
import esiag.back.models.dto.Chemin;
import esiag.back.models.dto.CheminSurEtage;
import esiag.back.models.medical.ActeMedical;
import esiag.back.models.medical.Salle;
import esiag.back.repositories.parcours.ConnexionRepository;
import esiag.back.repositories.parcours.EspaceRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CheminService {

    @Autowired
    private ConnexionRepository connexionRepository;
    @Autowired
    private EspaceRepository espaceRepository;
    @Autowired
    private ActeMedicalService acteMedicalService;
    @Autowired
    private SalleService salleService;
    @Autowired
    private ParcoursService parcoursService;

    public CheminService(ConnexionRepository connexionRepository) {
        this.connexionRepository = connexionRepository;
    }

    public Map<Long, List<Long>> getGrapheDuPlan(List<Connexion> connexions) {

        Map<Long, List<Long>> grapheDuPlan = new HashMap<>();

        for (Connexion connexion : connexions) {
            Long sommet1 = connexion.getEspace1().getIdEspace();
            Long sommet2 = connexion.getEspace2().getIdEspace();

            grapheDuPlan.putIfAbsent(sommet1, new ArrayList<>());
            grapheDuPlan.putIfAbsent(sommet2, new ArrayList<>());

            // Connexion bidirectionnelle
            grapheDuPlan.get(sommet1).add(sommet2);
            grapheDuPlan.get(sommet2).add(sommet1);

        }
        log.info("Construction du graphe terminé");
        return grapheDuPlan;
    }

    public List<Long> findCheminId(Long idDepart, Long idArrive, Map<Long, List<Long>> plan) {
        /*
         * Parcours du graphe en largeur pour trouver le chemin le plus cours entre
         * l'espace de départ
         * et tous les autres espaces.
         * La boucle s'arrête lorsqu'on visite l'espace d'arrivé.
         * 
         */
        List<Long> chemin = new ArrayList<>();

        Queue<Long> queue = new LinkedList<>();
        Set<Long> sommetsVisites = new HashSet<>();
        Map<Long, Long> arbreCouvrant = new HashMap<>();

        // Initialisation

        queue.add(idDepart);
        sommetsVisites.add(idDepart);
        arbreCouvrant.put(idDepart, null);

        // Parcours en largeur

        while (!queue.isEmpty()) {

            // On récupère le sommet courant
            Long sommetCourant = queue.poll();
            log.info("Salle courante : " + sommetCourant);

            // On s'arrête si on a atteint l'espace d'arrivée
            if (sommetCourant == idArrive) {
                log.info("Salle d'arrivée trouvée !!!!!!!!!!!");
                break;
            }

            for (Long voisin : plan.get(sommetCourant)) {
                if (!sommetsVisites.contains(voisin)) {
                    sommetsVisites.add(voisin);
                    arbreCouvrant.put(voisin, sommetCourant);
                    if (voisin == idArrive) {
                        break;
                        // On s'arrête dès que le sommet visité est l'espace d'arrivé
                    }
                    queue.add(voisin);
                }
            }

            log.info("Queue : " + queue);
            log.info("Sommets visités : " +sommetsVisites);
            log.info("Arbre couvrant :" +arbreCouvrant);
        }

        /*
         * Reconstruction du chemin à partir de l'arbre couvrant construit
         */
        Long sommet = idArrive;

        while (sommet != null) {
            chemin.add(sommet);
            sommet = arbreCouvrant.get(sommet);
        }

        log.info("Sommet en ID : "+ chemin);

        // Inversion du chemin pour avoir le bon ordre
        Collections.reverse(chemin);

        return chemin;
    }

    public List<Espace> findCheminEspace(Long idDepart, Long idArrive, Map<Long, List<Long>> plan){

        // Donne le chemin contenant uniquement les id des espaces

        List<Long> cheminId = findCheminId(idDepart, idArrive, plan);

        // Conversion des IDs en objets Espace
        List<Espace> cheminEspace = new ArrayList<>();

        for (Long id : cheminId) {
            Espace espace = espaceRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException(
                            "Espace introuvable pour l'id " + id));
            cheminEspace.add(espace);
        }

        return cheminEspace;

    }



    @Transactional
    public List<Chemin> nextActeMedical(Long idParcours, int ordre, Long idDepart) {

        if (ordre == 0) {

            parcoursService.updateStatutParcoursToEnCours(idParcours);
            log.info("Update statut global parcours to EN_COURS");

        }

        int ordreSuivant = ordre + 1;

        // Véfivier que le parcours est terminé
        List<ActeMedical> allActeMedicals = acteMedicalService.findAllActeMedicalsByParours(idParcours);

        if (ordre >= allActeMedicals.size()) {

            acteMedicalService.updatStatutActeMedicalToTermine();
            log.info("Update statut acte_medical to TERMINE");

            parcoursService.updateStatutParcoursToTermine(idParcours);
            log.info("Update statut parcours to TERMINE : " +idParcours);

            Salle salleActeCourant = salleService.findSallesByEspace(idDepart).get(0);
            salleService.updateSalleDecreasePlaceOccupee(salleActeCourant.getIdSalle());
            log.info("Libérer salle : " +salleActeCourant.getIdSalle());

            log.info("PARCOURS TERMINE");
            return null;
        }

        if(ordre != 0){
            // Mofification de l'état de l'acte médical courant à "TERMINE"
            acteMedicalService.updatStatutActeMedicalToTermine();
            log.info("Update statut acte_medical to TERMINE");

            // Modification de l'état de la salle associée à l'acte médical courant
            Salle salleActeCourant = salleService.findSallesByEspace(idDepart).get(0);
            salleService.updateSalleDecreasePlaceOccupee(salleActeCourant.getIdSalle());
            log.info("Libérer salle : " + salleActeCourant.getIdSalle());
        }
       
        /*
         * Récupère la salle associée à un acte médical
         */

        return rechercheSalleDisponible(idParcours, ordreSuivant, idDepart);

    }

    public Chemin cheminEnCoordonnees(List<Espace> cheminEspace, String numeroEtage) {
        Chemin chemin = new Chemin();
        chemin.setDebut(cheminEspace.get(0).getX() +" "+ cheminEspace.get(0).getY());
        chemin.setNumeroEtage(numeroEtage);
        String numeroEspaceChemin = "";

        for (Espace espace : cheminEspace) {
            chemin.setCoordonneesChemin(chemin.getCoordonneesChemin() + espace.getX() + " " + espace.getY() + " ");
            numeroEspaceChemin += espace.getNumeroEspace() + " ";
        }
        log.info("Chemin en coordonnées : " + chemin.getCoordonneesChemin());
        log.info("Chemin en numéro d'espace : " + numeroEspaceChemin);
        return chemin;
    }
    
    public List<Chemin> rechercheSalleDisponible(Long idParcours, int ordre, Long idDepart){

        /*
         * Récupère la salle associée à un acte médical
         */

        List<Espace> plusCourtChemin = new ArrayList<>();

        ActeMedical prochainActeMedical = acteMedicalService.findActeMedicalsByOrdre(ordre, idParcours)
                .get(0);

        List<Salle> salles = salleService
                .findSallesByTypeActeMedical(prochainActeMedical.getTypeActeMedical().getIdTypeActeMedical());

        List<List<Espace>> cheminsPossibles = new ArrayList<>();

        if (salles.isEmpty()) {
            List<Chemin> chemins = new ArrayList<>();
            Chemin chemin = new Chemin();
            chemin.setSalleDisponible(false);
            chemins.add(chemin);
            return chemins;
            // throw new IllegalStateException(
            // "Aucune salle disponible pour l'acte médical " +
            // prochainActeMedical.getIdActeMedical());
        } else {
            // Calcul des chemins vers chaque salle disponible

            if (idDepart == null) {
                List<Espace> entree = espaceRepository.findByNumeroEspace("SA1");

                Optional<Espace> entreePrincipale = Optional.ofNullable(entree.get(0));
                if (entreePrincipale.isPresent()) {
                    idDepart = entreePrincipale.get().getIdEspace();
                } else {
                    throw new IllegalStateException("L'espace de départ est nul.");
                }
            }

            List<Connexion> connexions = connexionRepository.findAll();
            log.info("Lecture des connexions");
            Map<Long, List<Long>> plan = getGrapheDuPlan(connexions);
            log.info("Construction du plan !!!");

            for (Salle salle : salles) {
                List<Espace> chemin = findCheminEspace(idDepart, salle.getEspace().getIdEspace(), plan);
                cheminsPossibles.add(chemin);
            }
        }

        // Trouver le chemin le plus court parmi les chemins possibles

        for (List<Espace> chemin : cheminsPossibles) {
            if (chemin.size() < plusCourtChemin.size() || plusCourtChemin.isEmpty()) {
                plusCourtChemin = chemin;
            }
        }
        log.info("Plus court chemin : ");
        for (Espace espace : plusCourtChemin) {
            log.info(espace.getIdEspace());
        }

        // Mofification de la capacité de la salle associée au prochain acte médical
        Espace espaceProchainActe = plusCourtChemin.get(plusCourtChemin.size() - 1);
        Salle salleProchainActe = salleService.findSallesByEspace(espaceProchainActe.getIdEspace()).get(0);

        // Mofification de la capacité de la salle associée au prochain acte médical
        salleService.updateSalleIncreasePlaceOccupee(salleProchainActe.getIdSalle());
        log.info("Capacité de la salle {} mise à jour pour le prochain acte médical.",
                salleProchainActe.getIdSalle());
        
        acteMedicalService.updateStatutActeMedicalToEncours(prochainActeMedical.getIdActeMedical(),
                salleProchainActe.getIdSalle());
        log.info("Update statut acte_medical to EN_COURS : " + prochainActeMedical.getIdActeMedical());

        //return cheminEnCoordonnees(plusCourtChemin);

        return diviserChemin(plusCourtChemin);
    }

    public List<Chemin> diviserChemin(List<Espace> cheminEspaces){

        List<Chemin> chemins = new ArrayList<>();
        List<CheminSurEtage> cheminSurEtages = new ArrayList<>();
        
        List<String> listNumeroEtages = new ArrayList<>();

        log.info("Identification des numéros d'étage");

        for(Espace espace : cheminEspaces){
            log.info("Espce de mon chemin --------->" +espace.getNumeroEspace() +" Etage --------> "+ espace.getEtage().getNumeroEtage());
            
            if(!listNumeroEtages.contains(espace.getEtage().getNumeroEtage())){
               
                listNumeroEtages.add(espace.getEtage().getNumeroEtage());
            }
        }

        log.info("List Etage ----------> "+listNumeroEtages);

        if (listNumeroEtages.size() == 1){
            CheminSurEtage cse = new CheminSurEtage();
            cse.setChemin(cheminEspaces);
            cse.setNumeroEtage(cheminEspaces.get(0).getEtage().getNumeroEtage());
            cheminSurEtages.add(cse);
        }else{
            List<Espace> espaceEtage1 = new ArrayList<>();
            List<Espace> espaceEtage2 = new ArrayList<>();
                
           
            for (Espace espace : cheminEspaces) {

                if (listNumeroEtages.get(0).equals(espace.getEtage().getNumeroEtage())) {

                    espaceEtage1.add(espace);
                    log.info("Espce de mon chemin --------->" +espace.getNumeroEspace() +" Etage --------> "+ espace.getEtage().getNumeroEtage());
                } else {
                    log.info("Espce de mon chemin --------->" +espace.getNumeroEspace() +" Etage --------> "+ espace.getEtage().getNumeroEtage());
                    espaceEtage2.add(espace);
                }

            }

            CheminSurEtage cse1 = new CheminSurEtage();

            cse1.setChemin(espaceEtage1);
            cse1.setNumeroEtage(listNumeroEtages.get(0));

            CheminSurEtage cse2 = new CheminSurEtage();

            cse2.setChemin(espaceEtage2);
            cse2.setNumeroEtage(listNumeroEtages.get(1));

            cheminSurEtages.add(cse1);
            cheminSurEtages.add(cse2);

        }

        log.info("Construction des différents chemins par étage");
        for (CheminSurEtage liste : cheminSurEtages) {

            Chemin chemin = cheminEnCoordonnees(liste.getChemin(), liste.getNumeroEtage());

            chemins.add(chemin);

            log.info("Chemin sur étage : " + liste.getNumeroEtage());

        }

        return chemins;

    }

}
