package com.escalade.victor.service;

import com.escalade.victor.model.Commentaire;
import com.escalade.victor.model.Reservation;
import com.escalade.victor.repository.CommentaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CommentaireService {

    @Autowired
    CommentaireRepository CommentaireRepository;

    public List<Commentaire> getAllCommentaires()
    {
        List<Commentaire> CommentaireList = CommentaireRepository.findAll();

        if(CommentaireList.size() > 0) {
            return CommentaireList;
        } else {
            return new ArrayList<Commentaire>();
        }
    }

    public void getCommentaireById(Long id)
    {
        Optional<Commentaire> Commentaire = CommentaireRepository.findById(id);

        if(Commentaire.isPresent()) {
            Commentaire existantCommentaire= Commentaire.get();

        }

    }

    public Commentaire createOrUpdateCommentaire(Commentaire Commentaire)
    {
        Optional<Commentaire> CommentaireRecherche = CommentaireRepository.findById(Commentaire.getId());

        if(CommentaireRecherche.isPresent())
        {
            Commentaire nouvelCommentaire = CommentaireRecherche.get();

            nouvelCommentaire = CommentaireRepository.save(nouvelCommentaire);

            return nouvelCommentaire;
        } else {
            Commentaire = CommentaireRepository.save(Commentaire);

            return Commentaire;
        }
    }

    public void deleteCommentaireById(Long id)
    {
        Optional<Commentaire> CommentaireEfface = CommentaireRepository.findById(id);

        if(CommentaireEfface.isPresent())
        {
            CommentaireRepository.deleteById(id);
        }
    }
}



