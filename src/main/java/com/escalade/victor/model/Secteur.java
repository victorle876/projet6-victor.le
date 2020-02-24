package com.escalade.victor.model;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
//import java.sql.Date;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "secteur")
public class Secteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du secteur est requis.")
    private String nomSecteur;

    @NotNull(message = "La hauteur est requise.")
    private Integer hauteur;

    @NotNull(message = "La cotation est requise.")
    private String cotation;

    @ManyToOne
    private Site site;

    @OneToMany(mappedBy="secteur")
    private List<Commentaire> commentaires;

    @OneToMany(mappedBy="secteur")
    private List<Voie> voies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull(message = "La longueur est requise.")
    private Integer Longueur;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = new Date();
        if (this.updatedAt == null) updatedAt = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Date();
    }


    public String getNomSecteur() {
        return nomSecteur;
    }

    public void setNomSecteur(String nomSecteur) {
        this.nomSecteur = nomSecteur;
    }

    public Integer getHauteur() {
        return hauteur;
    }

    public void setHauteur(Integer hauteur) {
        this.hauteur = hauteur;
    }

    public String getCotation() {
        return cotation;
    }

    public void setCotation(String cotation) {
        this.cotation = cotation;
    }

    public Integer getLongueur() {
        return Longueur;
    }

    public void setLongueur(Integer longueur) {
        this.Longueur = longueur;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Secteur{" +
                "id=" + id +
                ", nomSecteur='" + nomSecteur + '\'' +
                ", hauteur=" + hauteur +
                ", cotation=" + cotation +
                ", Longueur=" + Longueur +
                ", CreatedAt=" + createdAt +
                ", UpdatedAt=" + updatedAt +
                '}';
    }
}
