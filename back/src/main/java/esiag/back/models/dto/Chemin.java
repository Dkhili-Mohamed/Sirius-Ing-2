package esiag.back.models.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Chemin {


    private String coordonneesChemin = "";
    private String debut = "";
    private boolean salleDisponible = true;
    private String numeroEtage;

    public Chemin(String coordonneesChemin, String debut, boolean salleDisponible) {
        this.coordonneesChemin = coordonneesChemin;
        this.debut = debut;
        this.salleDisponible = salleDisponible;
    }   

}
