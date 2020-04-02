package com.escalade.victor.repository;
import com.escalade.victor.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopologieRepository extends JpaRepository<Topologie, Long>{

 //   List<Site> findByUtilisateurAndTopologieNull (Utilisateur utilisateur);
}
