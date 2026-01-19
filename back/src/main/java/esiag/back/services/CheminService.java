package esiag.back.services;


import esiag.back.models.architecture.Connexion;
import esiag.back.models.architecture.Espace;
import esiag.back.models.medical.Salle;
import esiag.back.repositories.ConnexionRepository;
import esiag.back.repositories.EspaceRepository;
import esiag.back.repositories.SalleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

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


    public CheminService(ConnexionRepository connexionRepository) {
        this.connexionRepository = connexionRepository;
    }

    public Map<Long, List<Long>> getGrapheDuPlan(){

        List<Connexion> connexions = connexionRepository.findAll();
        log.info("Lecture des connexions");

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

    public List<Espace> findChemin(Long idDepart, Long idArrive){
        /*
            Parcours du graphe en largeur pour trouver le chemin le plus cours entre l'espace de départ
            et tous les autres espaces.
            La boucle s'arrête lorsqu'on visite l'espace d'arrivé.

         */
        List<Long> chemin = new ArrayList<>();

        Queue<Long> queue = new LinkedList<>();
        Set<Long> sommetsVisites = new HashSet<>();
        Map<Long, Long> arbreCouvrant = new HashMap<>();
        Map<Long, List<Long>> grapheDuPlan =  getGrapheDuPlan();

        // Initialisation

        queue.add(idDepart);
        sommetsVisites.add(idDepart);
        arbreCouvrant.put(idDepart, null);

        // Parcours en largeur

        while (!queue.isEmpty()) {

            // On récupère le sommet courant
            Long sommetCourant = queue.poll();
            log.info("Salle courante : "+sommetCourant);

            // On s'arrête si on a atteint l'espace d'arrivée
            if (sommetCourant == idArrive) {
                log.info("Salle d'arrivée trouvée !!!!!!!!!!!");
                break;
            }

            for (Long voisin : grapheDuPlan.get(sommetCourant)) {
                if (!sommetsVisites.contains(voisin)) {
                    sommetsVisites.add(voisin);
                    arbreCouvrant.put(voisin, sommetCourant);
                    log.info("Salle d'arrivée trouvée !!!!!!!!!!!");
                    if (voisin == idArrive) {
                        break;
                        // On s'arrête dès que le sommet visité est l'espace d'arrivé
                    }
                    queue.add(voisin);
                }
            }
        }

        /*
            Reconstruction du chemin à partir de l'arbre couvrant construit
         */
        Long sommet = idArrive;

        while (sommet != null) {
            chemin.add(sommet);
            sommet = arbreCouvrant.get(sommet);
        }

        // Inversion du chemin pour avoir le bon ordre
        Collections.reverse(chemin);

        // Conversion des IDs en objets Espace
        List<Espace> cheminEspace = new ArrayList<>();

        for (Long id : chemin) {
            Espace espace = espaceRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException(
                            "Espace introuvable pour l'id " + id
                    ));
            cheminEspace.add(espace);
        }

        return cheminEspace;
    }


    public List<Espace> nextActeMedical(Long idTypeActeMedical, Long idActeMedecal, Long idDepart){

        //Mofification de l'état de l'acte médical courant à "TERMINE"
        acteMedicalService.updatStatutActeMedicalToTermine();

        // Modification de l'état de la salle associée à l'acte médical courant
        Salle salleActeCourant = salleService.findSallesByEspace(idDepart).get(0);
        salleService.updateSalleDecreasePlaceDisponible(salleActeCourant.getIdSalle());
        log.info("Capacité de la salle associée à l'acte médical {} mise à jour.", idDepart);
        /*
            Récupère la salle associée à un acte médical
         */

        List<Espace> plusCourtChemin = new ArrayList<>();
        List<Salle> salles = salleService.findSallesByTypeActeMedical(idTypeActeMedical);
        log.info("Salles récupérées {} pour l'acte médical {}.", salles, idTypeActeMedical);

        List<List<Espace>> cheminsPossibles = new ArrayList<>();

        if (salles.isEmpty()) {
            throw new IllegalStateException("Aucune salle disponible pour l'acte médical " + idActeMedecal);
        }else {
            // Calcul des chemins vers chaque salle disponible

            if(idDepart == null){
                List<Espace> entree = espaceRepository.findByNumeroEspace("SA1");
               
                Optional<Espace> entreePrincipale = Optional.ofNullable(entree.get(0));
                if (entreePrincipale.isPresent()) {
                    idDepart = entreePrincipale.get().getIdEspace();
                } else {
                    throw new IllegalStateException("L'espace de départ est nul.");
                }
            }
            for (Salle salle : salles) {
                List<Espace> chemin = findChemin(idDepart, salle.getEspace().getIdEspace());
                cheminsPossibles.add(chemin);
            }
        }

        // Trouver le chemin le plus court parmi les chemins possibles

        for (List<Espace> chemin : cheminsPossibles) {
            if(chemin.size() < plusCourtChemin.size() || plusCourtChemin.isEmpty()){
                plusCourtChemin = chemin;
            }
        }
        log.info("Plus court chemin calculé : {}.", plusCourtChemin);

        // Mofification de la capacité de la salle associée au prochain acte médical
        Espace espaceProchainActe = plusCourtChemin.get(plusCourtChemin.size() - 1);
        Salle salleProchainActe = salleService.findSallesByEspace(espaceProchainActe.getIdEspace()).get(0);

        // Modification de l'état de l'acte médical courant
        acteMedicalService.updateStatutActeMedicalToEncours(idActeMedecal, salleProchainActe.getIdSalle());
        
        // Mofification de la capacité de la salle associée au prochain acte médical
        salleService.updateSalleIncreasePlaceDisponible(salleProchainActe.getIdSalle());
        log.info("Capacité de la salle {} mise à jour pour le prochain acte médical.", salleProchainActe.getIdSalle());


        return plusCourtChemin;
    }

}
