package esiag.back.models.architecture;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "connexion")
public class Connexion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_connexion")
    private Long idConnexion;

    @ManyToOne
    @JoinColumn(name = "id_espace_1", nullable = false)
    private Espace espace1;

    @ManyToOne
    @JoinColumn(name = "id_espace_2", nullable = false)
    private Espace espace2; 

    @Override
    public String toString() {
        return "Connexion{" +
                "idConnexion=" + idConnexion +
                ", espace1=" + espace1 +
                ", espace2=" + espace2 +
                '}';               
    }
}
