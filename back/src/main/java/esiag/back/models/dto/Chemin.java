package esiag.back.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Chemin {


    private String coordonneesChemin;

    public Chemin(String coordonneesChemin) {
        this.coordonneesChemin = coordonneesChemin;
    }   

}
