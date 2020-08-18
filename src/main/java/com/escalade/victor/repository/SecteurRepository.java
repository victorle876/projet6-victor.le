package com.escalade.victor.repository;
import com.escalade.victor.model.Secteur;
import com.escalade.victor.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SecteurRepository extends JpaRepository<Secteur, Long> {
    List<Secteur> findByUtilisateur (Utilisateur utilisateur1);
    List<Secteur> findByNomSecteurIgnoreCaseContaining(String nomSecteur);
    List<Secteur> findBySite (Site site1);
}
