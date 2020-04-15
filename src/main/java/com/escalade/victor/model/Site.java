package com.escalade.victor.model;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "site")
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotBlank(message = "Le nom du site est requis.")
    @Column(name="nomSite")
    private String nomSite ;

    @ManyToOne
    @JoinColumn(name="topologie_id", referencedColumnName = "id")
    private Topologie topologie;

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


    @OneToMany(mappedBy="site")
    private List<Voie> voies;

    @OneToMany(mappedBy="site")
    private List<Commentaire> commentaires;

/*    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    private Utilisateur utilisateur;*/

    @ManyToOne
    @JoinColumn(name="utilisateur_id", referencedColumnName = "id")
    private Utilisateur utilisateur;

    public Site() {
    }

    /*    public Site(Long id, @NotBlank(message = "Le nom du site est requis.") String nomSite, Topologie topologie, Date createdAt, Date updatedAt, List<Voie> voies, List<Commentaire> commentaires, Utilisateur utilisateur) {
        this.id = id;
        this.nomSite = nomSite;
        this.topologie = topologie;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.voies = voies;
        this.commentaires = commentaires;
        this.utilisateur = utilisateur;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomSite() {
        return nomSite;
    }

    public void setNomSite(String nomSite) {
        this.nomSite = nomSite;
    }

    public Topologie getTopologie() {
        return topologie;
    }

    public void setTopologie(Topologie topologie) {
        this.topologie = topologie;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Voie> getVoies() {
        return voies;
    }

    public void setVoies(List<Voie> voies) {
        this.voies = voies;
    }

    public List<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }


    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", nomSite='" + nomSite + '\'' +
                ", topologie=" + topologie +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", voies=" + voies +
                ", commentaires=" + commentaires +
                ", utilisateur=" + utilisateur +
                '}';
    }

}
