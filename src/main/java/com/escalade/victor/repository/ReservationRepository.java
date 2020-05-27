package com.escalade.victor.repository;

import com.escalade.victor.model.*;
import com.escalade.victor.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUtilisateur (Utilisateur utilisateur1);
    Optional<Reservation> findByTopologie(Topologie topologieSelectionne);
}
