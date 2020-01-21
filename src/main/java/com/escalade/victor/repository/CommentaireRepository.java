package com.escalade.victor.repository;
import com.escalade.victor.model.*;
import com.escalade.victor.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
}
