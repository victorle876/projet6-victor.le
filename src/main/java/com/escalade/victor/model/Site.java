package com.escalade.victor.model;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "site")
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long id_secteur;


    @NotBlank(message = "La région du site est requise.")
    private String region_site ;

    @NotBlank(message = "Le nom du site est requise.")
    private String nom_site ;

    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", id_secteur=" + id_secteur +
                ", region_site='" + region_site + '\'' +
                ", nom_site='" + nom_site + '\'' +
                ", CreatedAt=" + CreatedAt +
                ", UpdatedAt=" + UpdatedAt +
                '}';
    }

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

    public String getRegion_site() {
        return region_site;
    }

    public void setRegion_site(String region_site) {
        this.region_site = region_site;
    }

    public String getNom_site() {
        return nom_site;
    }

    public void setNom_site(String nom_site) {
        this.nom_site = nom_site;
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

    private Timestamp CreatedAt;

    private Timestamp UpdatedAt;

}
