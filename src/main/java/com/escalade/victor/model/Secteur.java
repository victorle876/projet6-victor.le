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
    private String nomSecteur;

    @NotNull(message = "La hauteur est requise.")
    private Integer hauteur;

    @NotNull(message = "La cotation est requise.")
    private Integer cotation;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="site_id")
    private Site site;

    @OneToMany(mappedBy="commentaire")
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
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Timestamp createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Timestamp updatedAt;


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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
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
