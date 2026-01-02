package esiag.back.services;


import esiag.back.models.architecture.Connexion;
import esiag.back.repositories.ConnexionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class CheminService {

    @Autowired
    private ConnexionRepository connexionRepository;


    public CheminService(ConnexionRepository connexionRepository) {
        this.connexionRepository = connexionRepository;
    }

    public Map<Long, List<Long>> getGrapheDuPlan(){

        List<Connexion> connexions = connexionRepository.findAll();
        log.info("Lecture des connexions");

        Map<Long, List<Long>> grapheDuPlan = new HashMap<>();

        for (Connexion connexion : connexions) {
            Long sommet1 = connexion.getIdEspace1();
            Long sommet2 = connexion.getIdEspace2();

            grapheDuPlan.putIfAbsent(sommet1, new ArrayList<>());
            grapheDuPlan.putIfAbsent(sommet2, new ArrayList<>());

            // Connexion bidirectionnelle
            grapheDuPlan.get(sommet1).add(sommet2);
            grapheDuPlan.get(sommet2).add(sommet1);

        }
        log.info("Construction du graphe terminé");
        return grapheDuPlan;
    }





}
