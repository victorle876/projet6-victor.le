package com.escalade.victor.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static java.util.Arrays.*;

import java.util.Collection;


@Entity
@Table(name = "Utilisateurs")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotBlank(message = "Le nom est requis.")
    private String username;

    @NotBlank(message = "Le prenom est requis.")
    private String prenom;

    @NotBlank(message = "L'adresse mail est requis.")
    private String mail;

    private String password;

    @NotNull(message = "L''age est requis.")
    private Integer age;

   @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
   private List<Role> roles;

    @CreatedDate
    @Column(name = "createdAt")
    private Date createdAt;


    @LastModifiedDate
    @Column(name = "updatedAt")
    private Date updatedAt;
    
    @OneToMany(mappedBy="utilisateur")
    private List<Topologie> topologies;

    @OneToMany(mappedBy="utilisateur")
    private List<Commentaire> commentaires;

    @OneToMany(mappedBy="utilisateur")
    private List<Reservation> reservations;

    @OneToOne(mappedBy = "utilisateur", cascade = CascadeType.ALL,
    fetch = FetchType.LAZY, optional = false)
    private Site site;

/*    public Utilisateur(Long id, @NotBlank(message = "Le nom est requis.") String username, @NotBlank(message = "Le prenom est requis.") String prenom, @NotBlank(message = "L'adresse mail est requis.") String mail, String password, @NotNull(message = "L''age est requis.") Integer age, List<Role> roles, Date createdAt, Date updatedAt, List<Topologie> topologies, List<Commentaire> commentaires, List<Reservation> reservations) {
        this.id = id;
        this.username = username;
        this.prenom = prenom;
        this.mail = mail;
        this.password = password;
     //   this.age = age;
     //   this.roles = roles;
    //    this.createdAt = createdAt;
    //    this.updatedAt = updatedAt;
        this.topologies = topologies;
        this.commentaires = commentaires;
        this.reservations = reservations;
    }*/

    public Utilisateur() {
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

    public List<Topologie> getTopologies() {
        return topologies;
    }

    public void setTopologies(List<Topologie> topologies) {
        this.topologies = topologies;
    }

    public List<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", roles=" + roles +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", topologies=" + topologies +
                ", commentaires=" + commentaires +
                ", reservations=" + reservations +
                '}';
    }
}