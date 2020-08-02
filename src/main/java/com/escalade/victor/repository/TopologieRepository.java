package com.escalade.victor.repository;
import com.escalade.victor.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopologieRepository extends JpaRepository<Topologie, Long>{
    Optional<Topologie> findBySitesIsNull();
    List<Topologie> findByUtilisateur (Utilisateur utilisateur1);
    List<Topologie> findByIspublicTrue();
    List<Topologie> findByUtilisateurNot (Utilisateur utilisateur1);
    List <Topologie> findByAndIspublicTrueAndUtilisateurNot (Utilisateur utilisateur1);
    @Query("select t from Topologie t where t.nomTopologie like %:chaineRecherche%")
    List <Topologie> findByNomTopologieAndNomSecteur (String chaineRecherche);

    List <Topologie> findByNomTopologieIgnoreCaseContainingOrSecteurIgnoreCaseContaining(String nomTopologie, String secteur);
}
