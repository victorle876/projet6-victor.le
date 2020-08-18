package com.escalade.victor.repository;

import com.escalade.victor.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoieRepository extends JpaRepository<Voie, Long> {
    List<Voie> findBySecteur (Secteur secteur1);
    List<Voie> findByUtilisateur (Utilisateur utilisateur1);
    List <Voie> findByNomVoieIgnoreCaseContainingOrCotationIgnoreCaseContaining(String nomVoie, String cotation);
}
