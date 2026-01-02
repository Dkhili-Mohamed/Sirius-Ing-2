package esiag.back.services;


import esiag.back.models.architecture.Connexion;
import esiag.back.repositories.ConnexionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheminService {

    @Autowired
    private ConnexionRepository connexionRepository;

    public Map<Long, List<Long>> grapheDuPlan;

    public CheminService(ConnexionRepository connexionRepository) {
        this.connexionRepository = connexionRepository;
    }

    @PostConstruct
    public Map<Long, List<Long>> contructionGrapheDuPlan(){
        List<Connexion> connexions = connexionRepository.findAll();
        grapheDuPlan = new HashMap<>();

        for (Connexion connexion : connexions) {
            Long sommet1 = connexion.getIdEspace1();
            Long sommet2 = connexion.getIdEspace2();

            grapheDuPlan.putIfAbsent(sommet1, new ArrayList<>());
            grapheDuPlan.putIfAbsent(sommet2, new ArrayList<>());

            // Connexion bidirectionnelle
            grapheDuPlan.get(sommet1).add(sommet2);
            grapheDuPlan.get(sommet2).add(sommet1);

        }
        return grapheDuPlan;
    }

    public Map<Long, List<Long>> getGrapheDuPlan() {
        return contructionGrapheDuPlan();
    }





}
