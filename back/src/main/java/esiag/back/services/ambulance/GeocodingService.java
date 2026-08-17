package esiag.back.services.ambulance;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class GeocodingService {
    private final RestClient clientHttp = RestClient.create();

    // On va transformer une adresse en coordonnées GPS (latitude, longitude) en utilisant l'API Nominatim
    public double[] getCoordinates(String adresse) {
        try {
            // on envoie l'adresse à l'API Nominatim et on récupère la réponse en JSON
            String reponseEnJson = clientHttp.get()
                    .uri("https://nominatim.openstreetmap.org/search?q={adr}&format=json&limit=1", adresse)
                    .header("User-Agent", "Java Spring Boot App") 
                    .retrieve()
                    .body(String.class);

            // Nominatim nous renvoie un tableau de resultats 
            JSONArray tableauDeResultats = new JSONArray(reponseEnJson);
            

            // si on trouve au moins un résultat on ne prend que le premier
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
