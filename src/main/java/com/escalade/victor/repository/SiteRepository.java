package com.escalade.victor.repository;

import com.escalade.victor.model.Secteur;
import com.escalade.victor.model.Site;
import com.escalade.victor.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    List<Site> findByUtilisateurAndTopoIsNull (Utilisateur utilisateur1); // site
    List<Site> findByUtilisateur (Utilisateur utilisateur1);
    List<Site> findByNomSiteIsContaining(String nomSite);
    List<Site> findByNombreSecteurIgnoreCaseContaining(String nombreSecteur);
    List<Site> findByPaysIgnoreCaseContaining(String pays);
    List<Site> findByNombreSecteurIgnoreCaseContainingAndPaysIgnoreCaseContaining(String nombreSecteur, String pays);
    List<Site> findByNomSiteIgnoreCaseContainingOrNombreSecteurIgnoreCaseContainingOrPaysIgnoreCaseContaining(String nomSite, String nombreSecteur, String Pays);
}
