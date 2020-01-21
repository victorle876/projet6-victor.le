package com.escalade.victor.model;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "topologie")
public class Topologie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long id_site;

    private Long id_secteur;

    @NotBlank(message = "L'auteur'est requis.")
    private String auteur;

    @NotNull(message = "Le nombre de page est requis.")
    private Integer nombre_page;

    private Timestamp CreatedAt;

    private Timestamp UpdatedAt;


    public Long getId_secteur() {
        return id_secteur;
    }

    public void setId_secteur(Long id_secteur) {
        this.id_secteur = id_secteur;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Integer getNombre_page() {
        return nombre_page;
    }

    public void setNombre_page(Integer nombre_page) {
        this.nombre_page = nombre_page;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Topologie{" +
                "id=" + id +
                ", id_site=" + id_site +
                ", id_secteur=" + id_secteur +
                ", auteur='" + auteur + '\'' +
                ", nombre_page=" + nombre_page +
                ", CreatedAt=" + CreatedAt +
                ", UpdatedAt=" + UpdatedAt +
                '}';
    }
}
