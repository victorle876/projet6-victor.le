package com.escalade.victor.repository;
import com.escalade.victor.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopoRepository extends JpaRepository<Topo, Long>{
    Optional<Topo> findBySitesIsNull();
    List<Topo> findByUtilisateur (Utilisateur utilisateur1);
    List<Topo> findByIspublicTrue();
    List<Topo> findByUtilisateurNot (Utilisateur utilisateur1);
    List <Topo> findByAndIspublicTrueAndUtilisateurNot (Utilisateur utilisateur1);
    @Query("select t from Topo t where t.nomTopologie like %:chaineRecherche%")
    List <Topo> findByNomTopologie (String chaineRecherche);

    List <Topo> findByNomTopologieIgnoreCaseContaining(String nomTopologie);
}
