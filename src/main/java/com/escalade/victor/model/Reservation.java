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

    private Long id_utilisateur;

    private Long id_topologie;

    private String nom_preteur;

    private Date date_debut;

    private Date date_fin ;

    private Integer duree ;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(Long id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public Long getId_topologie() {
        return id_topologie;
    }

    public void setId_topologie(Long id_topologie) {
        this.id_topologie = id_topologie;
    }

    public String getNom_preteur() {
        return nom_preteur;
    }

    public void setNom_preteur(String nom_preteur) {
        this.nom_preteur = nom_preteur;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
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
                ", id_utilisateur=" + id_utilisateur +
                ", id_topologie=" + id_topologie +
                ", nom_preteur='" + nom_preteur + '\'' +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", duree=" + duree +
                '}';
    }
}
