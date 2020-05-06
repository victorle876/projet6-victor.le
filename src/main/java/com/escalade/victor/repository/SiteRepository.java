package com.escalade.victor.repository;
import com.escalade.victor.model.Site;
import com.escalade.victor.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    List<Site> findByUtilisateurAndTopologieIsNull (Utilisateur utilisateur1);
    List<Site> findByUtilisateur (Utilisateur utilisateur1);
}
