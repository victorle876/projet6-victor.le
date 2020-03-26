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
@Table(name = "topologie")
public class Topologie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

/*    @NotBlank(message = "L'auteur'est requis.")
    private String auteur;*/

    @NotBlank(message = "Le nom de la topologie est requise.")
    private String nomTopologie;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @LastModifiedDate
    private Date updatedAt;

    @NotBlank(message = "Le secteur du site est requis.")
    @Column(name="secteur")
    private String secteur ;

    @NotBlank(message = "Le pays est requis.")
    @Column(name="pays")
    private String pays ;

    @ManyToOne
    @JoinColumn(name="utilisateur_id", referencedColumnName = "id")
    private Utilisateur utilisateur;

    @OneToOne
    @JoinColumn(name="reservation_id", referencedColumnName = "id")
    private Reservation reservation ;

/*    @OneToOne // many
    @JoinColumn(name="site_id", referencedColumnName = "id")
    private Site site ;*/
    @OneToMany(mappedBy="topologie")
    private List<Site> sites;

    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = new Date();
        if (this.updatedAt == null) updatedAt = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Date();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomTopologie() {
        return nomTopologie;
    }

    public void setNomTopologie(String nomTopologie) {
        this.nomTopologie = nomTopologie;
    }


    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

/*    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }*/

    public List<Site> getSites() {
        return sites;
    }

    public void setSites(List<Site> sites) {
        this.sites = sites;
    }

    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    @Override
    public String toString() {
        return "Topologie{" +
                "id=" + id +
                ", CreatedAt=" + createdAt +
                ", UpdatedAt=" + updatedAt +
                '}';
    }
}
