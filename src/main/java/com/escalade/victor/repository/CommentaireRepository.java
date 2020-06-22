package com.escalade.victor.repository;
import com.escalade.victor.model.*;
import com.escalade.victor.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
    List<Commentaire> findBySite (Site site1);
}
