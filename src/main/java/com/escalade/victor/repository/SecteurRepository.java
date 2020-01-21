package com.escalade.victor.repository;

import com.escalade.victor.model.Secteur;
import com.escalade.victor.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecteurRepository extends JpaRepository<Secteur, Long> {
}
