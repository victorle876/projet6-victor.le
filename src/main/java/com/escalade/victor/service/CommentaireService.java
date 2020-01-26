package com.escalade.victor.service;

import com.escalade.victor.model.Commentaire;
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
        Optional<Commentaire> Commentaire1 = CommentaireRepository.findById(id);

        if(Commentaire1.isPresent()) {
            return Commentaire1.get();
        }

    }

    public Commentaire createOrUpdateCommentaire(Commentaire Commentaire)
    {
        Optional<Commentaire> CommentaireRecherche = CommentaireRepository.findById(Commentaire.getId());

        if(CommentaireRecherche.isPresent())
        {
            Commentaire nouvelCommentaire = CommentaireRecherche.get();
            nouvelCommentaire.setZone_Commentaire(Commentaire.getZone_Commentaire());
            nouvelCommentaire.setAuteur_commentaire(Commentaire.getAuteur_commentaire());
            nouvelCommentaire.setCreatedAt(Commentaire.getCreatedAt());
            nouvelCommentaire.setUpdatedAt(Commentaire.getUpdatedAt());

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



