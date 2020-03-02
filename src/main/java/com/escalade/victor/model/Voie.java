package com.escalade.victor.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "voie")
public class Voie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomVoie;

    @NotNull(message = "La cotation est requise.")
    private String cotation;

    @ManyToOne
    private Site site;

    @NotNull(message = "La hauteur est requise.")
    private Integer hauteur;

    @NotNull(message = "La distance est requise.")
    private Integer Distance;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomVoie() {
        return nomVoie;
    }

    public void setNomVoie(String nomVoie) {
        this.nomVoie = nomVoie;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
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

    public String getCotation() {
        return cotation;
    }

    public void setCotation(String cotation) {
        this.cotation = cotation;
    }

    @Override
    public String toString() {
        return "Voie{" +
                "id=" + id +
                ", nomVoie='" + nomVoie + '\'' +
                ", cotation='" + cotation + '\'' +
                ", site=" + site +
                ", hauteur=" + hauteur +
                ", Distance=" + Distance+
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }



}
