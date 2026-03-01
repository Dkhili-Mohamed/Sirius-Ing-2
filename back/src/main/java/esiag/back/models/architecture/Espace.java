package esiag.back.models.architecture;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "espace")
public class Espace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_espace")
    private Long idEspace;

    @Column(name = "numero_espace")
    private String numeroEspace;

    // @Enumerated(EnumType.STRING)
    // @Column(name = "type_espace")
    // private TypeEspace typeEspace;

    @ManyToOne
    @JoinColumn(name = "id_etage", nullable = false)
    private Etage etage;

    @Column
    private int x;

    @Column
    private int y;

    @Override
    public String toString() {
        return "Espace [idEspace=" + idEspace + ", numeroEspace=" + numeroEspace + ", etage=" + etage + ", x=" + x
                + ", y=" + y + "]";
    }

    

}
