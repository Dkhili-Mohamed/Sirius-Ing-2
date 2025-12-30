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

    @Column(name = "id_espace_1")
    private Long idEspace1;

    @Column(name = "id_espace_2")
    private Long idEspace2;

    @Override
    public String toString() {
        return "Connexion{" +
                "idConnexion=" + idConnexion +
                ", idEspace1=" + idEspace1 +
                ", idEspace2=" + idEspace2 +
                '}';
    }
}
