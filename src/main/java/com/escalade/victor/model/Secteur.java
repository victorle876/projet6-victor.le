package com.escalade.victor.model;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "secteur")
public class Secteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du secteur est requis.")
    private String nom_secteur;

    @NotNull(message = "La hauteur est requise.")
    private Integer hauteur;

    @NotNull(message = "La cotation est requise.")
    private Integer cotation;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="site_id")
    private Site site1;

    @OneToMany(mappedBy="commentaire")
    private List<Commentaire> commentaires;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getNom_Secteur() {
        return nom_secteur;
    }

    public void setNom_Secteur(String nom_secteur) {
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
                ", nom_secteur='" + nom_secteur + '\'' +
                ", hauteur=" + hauteur +
                ", cotation=" + cotation +
                ", Longueur=" + Longueur +
                ", CreatedAt=" + CreatedAt +
                ", UpdatedAt=" + UpdatedAt +
                '}';
    }
}
