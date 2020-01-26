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

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="utilisateur_id")
    private Utilisateur utilisateur1;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="secteur_id")
    private Secteur secteur1;

    private String Zone_Commentaire;

    public String getZone_Commentaire() {
        return Zone_Commentaire;
    }

    public void setZone_Commentaire(String zone_Commentaire) {
        Zone_Commentaire = zone_Commentaire;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
