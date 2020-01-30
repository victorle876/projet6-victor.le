package com.escalade.victor.model;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer duree ;


    @OneToOne
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    private Utilisateur utilisateur;

    @OneToOne
    @JoinColumn(name = "topologie_id", referencedColumnName = "id")
    private Topologie topologie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
/*                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +*/
                ", duree=" + duree +
                '}';
    }
}
