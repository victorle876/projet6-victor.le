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
    private Integer cotation;

    @ManyToOne
    private Site site;

    @OneToMany(mappedBy="secteur")
    private List<Commentaire> commentaires;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull(message = "La longueur est requise.")
    private Integer Longueur;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @LastModifiedDate
    private Date updatedAt;


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
