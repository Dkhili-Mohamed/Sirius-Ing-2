package esiag.back.models.medical;

import lombok.Getter;
import java.util.Random;

@Getter
public enum NiveauUrgence {
    NON_URGENT(120,300), INTERMEDIAIRE(90,150), URGENT(60,90);

    private final int minSecondes;
    private final int maxSecondes;
    private final Random tempsConsultationAleatoire = new Random();

     NiveauUrgence(int minSecondes, int maxSecondes) {
        this.minSecondes = minSecondes;
        this.maxSecondes = maxSecondes;
    }

    public int getTempsAleatoire(){
        return tempsConsultationAleatoire.nextInt(maxSecondes-minSecondes+1) + minSecondes;
    }

    public int getTempsMoyen(){
         return (minSecondes + maxSecondes) / 2;
    }

}
