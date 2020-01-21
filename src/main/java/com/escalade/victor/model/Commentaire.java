package com.escalade.victor.model;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "commentaire")
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long id_secteur;

    private Long id_utilisateur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_secteur() {
        return id_secteur;
    }

    public void setId_secteur(Long id_secteur) {
        this.id_secteur = id_secteur;
    }

    public Long getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(Long id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getAuteur_commentaire() {
        return auteur_commentaire;
    }

    public void setAuteur_commentaire(String auteur_commentaire) {
        this.auteur_commentaire = auteur_commentaire;
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

    private String auteur_commentaire;

    private Timestamp CreatedAt;

    private Timestamp UpdatedAt;

}
