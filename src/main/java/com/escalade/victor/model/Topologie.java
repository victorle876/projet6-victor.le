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

    @NotBlank(message = "L'auteur'est requis.")
    private String auteur;

    @NotNull(message = "Le nom de la topologie est requise.")
    private String nomTopolgie;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Timestamp createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Timestamp updatedAt;

    @OneToOne
    @JoinColumn(name="utilisateur_id", referencedColumnName = "id")
    private Utilisateur utilisateur;

    @OneToOne
    @JoinColumn(name="reservation_id", referencedColumnName = "id")
    private Reservation reservation ;

    @OneToOne
    @JoinColumn(name="site_id", referencedColumnName = "id")
    private Site site ;

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
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

    public String getNomTopolgie() {
        return nomTopolgie;
    }

    public void setNomTopolgie(String nomTopolgie) {
        this.nomTopolgie = nomTopolgie;
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
