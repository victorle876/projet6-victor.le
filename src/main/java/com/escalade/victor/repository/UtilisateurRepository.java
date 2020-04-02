package com.escalade.victor.repository;

import com.escalade.victor.model.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByMail(String mail);

    @Query("select id from Utilisateur u where u.mail = :username1")
    Utilisateur findUserById (String username1);
}
