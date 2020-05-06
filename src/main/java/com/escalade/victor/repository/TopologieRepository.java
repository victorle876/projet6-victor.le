package com.escalade.victor.repository;
import com.escalade.victor.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopologieRepository extends JpaRepository<Topologie, Long>{
    Optional<Topologie> findBySitesIsNull();
    List<Topologie> findTopologieByUtilisateur (Utilisateur utilisateur1);
}
