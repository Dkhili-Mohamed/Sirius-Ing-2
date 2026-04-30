
package esiag.back.services.parcours;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import esiag.back.models.medical.Medecin;
import esiag.back.models.medical.Parcours;
import esiag.back.models.medical.ActeMedical;
import esiag.back.models.medical.StatutActeMedical;
import net.bytebuddy.asm.Advice.Local;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParcoursIntegrationTest {

    // lien de la vidéo youtube qui m'a aidé à écrire ce test :
    // https://www.youtube.com/watch?v=Hh17JDpsKqc

    @LocalServerPort
    private int port;

    private static RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ParcoursService parcoursService;


    @Test
    public void testInsertParcours() {

        Parcours parcours = new Parcours();
        Medecin medecin = new Medecin();
        medecin.setIdMedecin(10L);

        parcours.setMedecin(medecin);
        parcours.setStatutGlobal(StatutActeMedical.EN_ATTENTE);
        parcours.setNomParcours("Examens généraux");
        parcours.setDateCreation(System.currentTimeMillis());

        String localHost = "http://localhost:" + port + "/api/parcours/insert";

        Parcours response = restTemplate.postForObject(localHost, parcours, Parcours.class);
        
        assertEquals("Examens généraux", response.getNomParcours());
        assertEquals(StatutActeMedical.EN_ATTENTE, response.getStatutGlobal());

        
    }



    @Test
    public void testFindActeMedicalByIdPatient() {

        String localHost = "http://localhost:" + port + "/api/type-acte-medical/1";

        List<ActeMedical> response = restTemplate.getForObject(localHost, List.class);
        
        assertNotNull(response);
        assertFalse(response.isEmpty());
        

    }


}
