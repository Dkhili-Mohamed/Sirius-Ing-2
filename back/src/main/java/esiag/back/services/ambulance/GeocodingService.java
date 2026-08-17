package esiag.back.services.ambulance;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodingService {
    private final RestTemplate restTemplate = new RestTemplate();

    // On va transformer une adresse en coordonnées GPS (latitude, longitude) en utilisant l'API Nominatim
    public double[] getCoordinates(String adresse) {
        try {
            // on construit l'URL avec l'adresse 
            String url = "https://nominatim.openstreetmap.org/search?q={adr}&format=json&limit=1";

            // on met un User-agent dans les en-têtes car l'API l'exige
            HttpHeaders entetes = new HttpHeaders();
            entetes.set("User-Agent", "Java Spring Boot App");
            HttpEntity<String> requete = new HttpEntity<>(entetes);

            // on envoie la requête et on recupère la réponse en JSON
            ResponseEntity<String> reponse = restTemplate.exchange(url, HttpMethod.GET, requete, String.class, adresse);
            String reponseEnJson = reponse.getBody();

            // Nominatim nous renvoie un tableau de resultats 
            JSONArray tableauDeResultats = new JSONArray(reponseEnJson);
            

            // si on trouve au moins un resultat on ne prend que le premier
            if (tableauDeResultats.length() > 0) {
                JSONObject premierResultat = tableauDeResultats.getJSONObject(0);
                double latitude = Double.parseDouble(premierResultat.getString("lat"));
                double longitude = Double.parseDouble(premierResultat.getString("lon"));

                // on renvoie les deux valeurs dans un tableau
                return new double[]{latitude, longitude};
            } else {
                return null; 
            }
        // Gestion des erreurs
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }  
}
