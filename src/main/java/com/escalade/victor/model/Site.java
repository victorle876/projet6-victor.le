package com.escalade.victor.model;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "site")
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotBlank(message = "Le nom du site est requise.")
    @Column(name="nomSite")
    private String nomSite ;

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

    @OneToOne
    @JoinColumn(name = "topologie_id", referencedColumnName = "id")
    private Topologie topologie;


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


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        updatedAt = updatedAt;
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

    public Topologie getTopologie() {
        return topologie;
    }

    public void setTopologie(Topologie topologie) {
        this.topologie = topologie;
    }

    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", nomSite='" + nomSite + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", voies=" + voies +
                ", commentaires=" + commentaires +
                '}';
    }


}
