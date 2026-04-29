
package esiag.back.services.parcours;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import esiag.back.models.architecture.Connexion;
import esiag.back.models.architecture.Espace;
import esiag.back.models.architecture.Etage;
import esiag.back.models.dto.Chemin;


import esiag.back.models.architecture.Etage;

public class CheminServiceTest {

    final private CheminService cheminService = new CheminService(null);


    @Test
    public void testCalculerChemin1Etage() {

        // Création des deux étages

        Etage etage1 = new Etage();
        etage1.setIdEtage(1L);
        etage1.setNumeroEtage("Etage 1");

        // Création des espaces associés aux étages

        Espace espace1 = new Espace();
        espace1.setIdEspace(1L);
        espace1.setEtage(etage1);
        espace1.setX(0);
        espace1.setY(0);

        Espace espace2 = new Espace();
        espace2.setIdEspace(2L);
        espace2.setEtage(etage1);
        espace2.setX(20);
        espace2.setY(20);

        Espace espace3 = new Espace();
        espace3.setIdEspace(3L);
        espace3.setEtage(etage1);
        espace3.setX(30);
        espace3.setY(30);

        Espace espace4 = new Espace();
        espace4.setIdEspace(4L);
        espace4.setEtage(etage1);
        espace4.setX(40);
        espace4.setY(40);

        // Le chemin en espace
        List<Espace> espaces = Arrays.asList(espace1, espace2, espace3, espace4);

        List<Chemin> chemins = cheminService.diviserChemin(espaces);

        // Vérification des résultats
        assertNotNull(chemins);
        assertEquals(1, chemins.size());
        assertEquals("0 0 20 20 30 30 40 40 ", chemins.get(0).getCoordonneesChemin());

    }



    @Test
    public void testCalculerChemin2Etage() {

        // Création des deux étages

        Etage etage1 = new Etage();
        etage1.setIdEtage(1L);
        etage1.setNumeroEtage("Etage 1");

        Etage etage2 = new Etage();
        etage2.setIdEtage(2L);
        etage2.setNumeroEtage("Etage 2");

        // Création des espaces associés aux étages

        Espace espace1 = new Espace();
        espace1.setIdEspace(1L);
        espace1.setEtage(etage1);
        espace1.setX(0);
        espace1.setY(0);

        Espace espace2 = new Espace();
        espace2.setIdEspace(2L);
        espace2.setEtage(etage1);
        espace2.setX(20);
        espace2.setY(20);

        Espace espace3 = new Espace();
        espace3.setIdEspace(3L);
        espace3.setEtage(etage1);
        espace3.setX(30);
        espace3.setY(30);

        Espace espace4 = new Espace();
        espace4.setIdEspace(4L);
        espace4.setEtage(etage1);
        espace4.setX(40);
        espace4.setY(40);

        Espace espace5 = new Espace();
        espace5.setIdEspace(5L);
        espace5.setEtage(etage2);
        espace5.setX(50);
        espace5.setY(50);

        Espace espace6 = new Espace();
        espace6.setIdEspace(6L);
        espace6.setEtage(etage2);
        espace6.setX(60);
        espace6.setY(60);

        Espace espace7 = new Espace();
        espace7.setIdEspace(7L);
        espace7.setEtage(etage2);
        espace7.setX(70);
        espace7.setY(70);

        // Le chemin en espace
        List<Espace> espaces = Arrays.asList(espace1, espace2, espace3, espace4, espace5, espace6, espace7);


        List<Chemin> chemins = cheminService.diviserChemin(espaces);

        // Vérification des résultats
        assertNotNull(chemins);
        assertEquals(2, chemins.size());
        assertEquals("0 0 20 20 30 30 40 40 ", chemins.get(0).getCoordonneesChemin());
        assertEquals("50 50 60 60 70 70 ", chemins.get(1).getCoordonneesChemin());
        
        

    }

    @Test
    public void testFindCheminId(){

        // Création d'un étage

        Etage etage1 = new Etage();
        etage1.setIdEtage(12L);
        etage1.setNumeroEtage("Etage 1");

        // Création des espaces associés aux étages

        Espace espace1 = new Espace();
        espace1.setIdEspace(1L);
        espace1.setEtage(etage1);

        Espace espace2 = new Espace();
        espace2.setIdEspace(2L);
        espace2.setEtage(etage1);

        Espace espace3 = new Espace();
        espace3.setIdEspace(3L);
        espace3.setEtage(etage1);

        Espace espace4 = new Espace();
        espace4.setIdEspace(4L);
        espace4.setEtage(etage1);

        Espace espace5 = new Espace();
        espace5.setIdEspace(5L);
        espace5.setEtage(etage1);


        Espace espace6 = new Espace();
        espace6.setIdEspace(6L);
        espace6.setEtage(etage1);


        Espace espace7 = new Espace();
        espace7.setIdEspace(7L);
        espace7.setEtage(etage1);
 

        Espace espace8 = new Espace();
        espace8.setIdEspace(8L);
        espace8.setEtage(etage1);
 

        /*

            GRAPHE CONSTRUIT A PARTIR DES ESPACES ET DES CONNEXIONS
                  8
                  |
            1--2--4--6
            |  |
            3  5--7

        */

        Connexion con1 = new Connexion();
        con1.setEspace1(espace1);
        con1.setEspace2(espace2);

        Connexion c2 = new Connexion();
        c2.setEspace1(espace1);
        c2.setEspace2(espace3);

        Connexion con3 = new Connexion();
        con3.setEspace1(espace2);
        con3.setEspace2(espace5);

        Connexion c4 = new Connexion();
        c4.setEspace1(espace5);
        c4.setEspace2(espace7);

        Connexion con5 = new Connexion();
        con5.setEspace1(espace2);
        con5.setEspace2(espace4);

        Connexion c6 = new Connexion();
        c6.setEspace1(espace4);
        c6.setEspace2(espace8);

        Connexion con7 = new Connexion();
        con7.setEspace1(espace4);
        con7.setEspace2(espace6);

        List<Connexion> connexions = Arrays.asList(con1,c2,con3,c4,con5, c6,con7);
        Map<Long, List<Long>> plan = cheminService.getGrapheDuPlan(connexions);

        List<Long> chemin1 = cheminService.findCheminId(1L, 7L, plan);
        List<Long> chemin2 = cheminService.findCheminId(7L, 8L, plan);

        assertNotNull(chemin1);
        assertNotNull(chemin2);

        
        List<Long> chm1 = Arrays.asList(1L,2L,5L,7L);
        List<Long> chm2 = Arrays.asList(7L,5L,2L,4L,8L);

        assertEquals(chm1,chemin1);
        assertEquals(chm2,chemin2);


    }

}
