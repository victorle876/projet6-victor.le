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

    @OneToOne
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    private Utilisateur utilisateur;

    @OneToOne
    @JoinColumn(name = "secteur_id", referencedColumnName = "id")
    private Secteur secteur;

    private String zoneCommentaire;

    private Timestamp CreatedAt;

    private Timestamp UpdatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZoneCommentaire() {
        return zoneCommentaire;
    }

/*    public void setZoneCommentaire(String zoneCommentaire) {
        zoneCommentaire = zoneCommentaire;
    }*/

    public Timestamp getCreatedAt() {
        return CreatedAt;
    }

/*    public void setCreatedAt(Timestamp CreatedAt) {
        CreatedAt = CreatedAt;
    }*/

    public Timestamp getUpdatedAt() {
        return UpdatedAt;
    }

/*    public void setUpdatedAt(Timestamp updatedAt) {
        UpdatedAt = updatedAt;
    }*/


}
