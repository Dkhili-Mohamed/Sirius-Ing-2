package esiag.back.services;


import esiag.back.models.architecture.Connexion;
import esiag.back.models.architecture.Espace;
import esiag.back.repositories.ConnexionRepository;
import esiag.back.repositories.EspaceRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.*;

@Log4j2
@Service
public class CheminService {

    @Autowired
    private ConnexionRepository connexionRepository;
    @Autowired
    private EspaceRepository espaceRepository;


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

            // On s'arrête si on a atteint l'espace d'arrivée
            if (sommetCourant == idArrive) {
                break;
            }

            for (Long voisin : grapheDuPlan.get(sommetCourant)) {
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

}
