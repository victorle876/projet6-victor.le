package com.escalade.victor.service;

import com.escalade.victor.model.Commentaire;
import com.escalade.victor.model.Reservation;
import com.escalade.victor.model.Site;
import com.escalade.victor.model.Utilisateur;
import com.escalade.victor.repository.CommentaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CommentaireService {

    @Autowired
    CommentaireRepository commentaireRepository;

    private Utilisateur utilisateurRecherche;

    private Site siteRecherche;

    public List<Commentaire> getAllCommentaires()
    {
        List<Commentaire> CommentaireList = commentaireRepository.findAll();

        if(CommentaireList.size() > 0) {
            return CommentaireList;
        } else {
            return new ArrayList<Commentaire>();
        }
    }

    public Commentaire getCommentaireById(Long id)
    {
        return this.commentaireRepository.findById(id).get();

    }

    public Commentaire saveCommentaire(Commentaire commentaire)
    {

            return this.commentaireRepository.save(commentaire);
    }

    public void addUtilComm(Utilisateur utilisateur, long id)
    {
        this.utilisateurRecherche = utilisateur;
        if (!utilisateurRecherche.getCommentaires().contains(this)){
            utilisateurRecherche.getCommentaires().add(this.getCommentaireById(id));
        }
    }

    public void addSiteComm(Site site, long id)
    {
        this.siteRecherche = site;
        if (!siteRecherche.getCommentaires().contains(this)){
            siteRecherche.getCommentaires().add(this.getCommentaireById(id));
        }
    }


    public void deleteCommentaireById(Long id)
    {
/*        Optional<Commentaire> CommentaireEfface = commentaireRepository.findById(id);

        if(CommentaireEfface.isPresent())
        {*/
            commentaireRepository.deleteById(id);
   //     }
    }
}



