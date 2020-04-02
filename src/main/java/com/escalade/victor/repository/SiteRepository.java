package com.escalade.victor.repository;
import com.escalade.victor.model.Site;
import com.escalade.victor.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    List<Site> findByUtilisateur(Utilisateur utilisateur1);
}
