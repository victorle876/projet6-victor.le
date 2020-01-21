package com.escalade.victor.model;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "secteur")
public class Secteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long id_site;

    private Long id_commentaire;


    @NotBlank(message = "Le nom du secteur est requis.")
    private String nom_secteur;

    @NotNull(message = "La hauteur est requise.")
    private Integer hauteur;

    @NotNull(message = "La cotation est requise.")
    private Integer cotation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_site() {
        return id_site;
    }

    public void setId_site(Long id_site) {
        this.id_site = id_site;
    }

    public Long getId_commentaire() {
        return id_commentaire;
    }

    public void setId_commentaire(Long id_commentaire) {
        this.id_commentaire = id_commentaire;
    }

    public String getNom_secteur() {
        return nom_secteur;
    }

    public void setNom_secteur(String nom_secteur) {
        this.nom_secteur = nom_secteur;
    }

    public Integer getHauteur() {
        return hauteur;
    }

    public void setHauteur(Integer hauteur) {
        this.hauteur = hauteur;
    }

    public Integer getCotation() {
        return cotation;
    }

    public void setCotation(Integer cotation) {
        this.cotation = cotation;
    }

    public Integer getLongueur() {
        return Longueur;
    }

    public void setLongueur(Integer longueur) {
        Longueur = longueur;
    }

    public Timestamp getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        CreatedAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        UpdatedAt = updatedAt;
    }

    @NotNull(message = "La longueur est requise.")
    private Integer Longueur;

    private Timestamp CreatedAt;

    private Timestamp UpdatedAt;

    @Override
    public String toString() {
        return "Secteur{" +
                "id=" + id +
                ", id_site=" + id_site +
                ", id_commentaire=" + id_commentaire +
                ", nom_secteur='" + nom_secteur + '\'' +
                ", hauteur=" + hauteur +
                ", cotation=" + cotation +
                ", Longueur=" + Longueur +
                ", CreatedAt=" + CreatedAt +
                ", UpdatedAt=" + UpdatedAt +
                '}';
    }
}
