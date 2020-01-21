package com.escalade.victor.repository;

import com.escalade.victor.model.*;
import com.escalade.victor.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
