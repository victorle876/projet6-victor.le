package com.escalade.victor.model;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

/*    @OneToOne
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    private Utilisateur utilisateur;*/

    @ManyToOne
    private Secteur secteur;

    @ManyToOne
    private Utilisateur utilisateur;

    private String zoneCommentaire;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date CreatedAt;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @LastModifiedDate
    private Date UpdatedAt;

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

    public Date getCreatedAt() {
        return CreatedAt;
    }

/*    public void setCreatedAt(Timestamp CreatedAt) {
        CreatedAt = CreatedAt;
    }*/

    public Date getUpdatedAt() {
        return UpdatedAt;
    }

/*    public void setUpdatedAt(Timestamp updatedAt) {
        UpdatedAt = updatedAt;
    }*/


}
