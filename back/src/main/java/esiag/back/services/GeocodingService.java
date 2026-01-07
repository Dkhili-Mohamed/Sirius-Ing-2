package esiag.back.services;

import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class GeocodingService {

    public double[] getCoordinates(String adresse) {
        try {
            
            String encodedAddress = URLEncoder.encode(adresse, "UTF-8");

            
            String urlStr = "https://nominatim.openstreetmap.org/search?q=" + encodedAddress + "&format=json&limit=1";

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Java Spring Boot App"); // Obligatoire pour Nominatim

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            
            JSONArray jsonArray = new JSONArray(response.toString());
            if (jsonArray.length() > 0) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                double lat = Double.parseDouble(jsonObject.getString("lat"));
                double lon = Double.parseDouble(jsonObject.getString("lon"));
                return new double[]{lat, lon};
            } else {
                return null; 
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }  //j'ai fait la moitié avec chatgbt 
}
