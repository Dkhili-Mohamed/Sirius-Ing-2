
package esiag.back.services.parcours;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import esiag.back.models.architecture.Espace;
import esiag.back.models.architecture.Etage;
import esiag.back.models.dto.Chemin;


import esiag.back.models.architecture.Etage;

public class CheminServiceTest {

    private CheminService cheminService = new CheminService(null);


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

}
