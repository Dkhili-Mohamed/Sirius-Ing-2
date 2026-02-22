package esiag.back.models.medical;

import lombok.Getter;

@Getter
public enum NiveauUrgence {
    NON_URGENT(3,0), INTERMEDIAIRE(2,30), URGENT(1,30);

    private final int minutes;
    private final int secondes;

     NiveauUrgence(int minutes, int secondes) {
        this.minutes = minutes;
        this.secondes = secondes;
    }

    public int getTemps() {
         return minutes*60 + secondes;
    }

    public int getMinutes(){
         return minutes;
    }

    public int getSecondes(){
        return secondes;
    }

}
