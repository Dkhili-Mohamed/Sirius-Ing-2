package esiag.back.models.dto;

import java.util.List;

import esiag.back.models.architecture.Espace;
import lombok.Data;

@Data
public class CheminSurEtage {


    private List<Espace> chemin;
    private String numeroEtage;


    public CheminSurEtage (List<Espace> chemin, String numeroEtage){
        this.chemin = chemin;
        this.numeroEtage = numeroEtage;
    }

    public CheminSurEtage(){};

}
