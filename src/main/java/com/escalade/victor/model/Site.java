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
@Table(name = "site")
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La région du site est requise.")
    private String regionSite ;

    @NotBlank(message = "Le nom du site est requise.")
    private String nomSite ;

    @NotBlank(message = "Le nom du site est requise.")
    private String pays ;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @LastModifiedDate
    private Date updatedAt;

    @OneToMany(mappedBy="site")
    private List<Secteur> secteurs;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegionSite() {
        return regionSite;
    }

    public void setRegionSite(String region_site) {
        this.regionSite = regionSite;
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

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", region_site='" + regionSite + '\'' +
                ", nom_site='" + nomSite + '\'' +
                ", CreatedAt=" + createdAt +
                ", UpdatedAt=" + updatedAt +
                '}';
    }

}
