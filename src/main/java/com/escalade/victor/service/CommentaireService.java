package com.escalade.victor.service;

import com.escalade.victor.model.Commentaire;
import com.escalade.victor.model.Reservation;
import com.escalade.victor.model.Site;
import com.escalade.victor.model.Utilisateur;
import com.escalade.victor.repository.CommentaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    /**
     * Méthode permet de lister tous les commentaires via ce service
     *
     * * @return la liste des commentaires
     */
    public List<Commentaire> getAllCommentaires()
    {
        List<Commentaire> CommentaireList = commentaireRepository.findAll();

        if(CommentaireList.size() > 0) {
            return CommentaireList;
        } else {
            return new ArrayList<Commentaire>();
        }
    }

    /**
     * Méthode permet de consulter le commentaire en fonction de l'id via ce service
     *
     * @param id
     * * @return le commentaire via id
     */
    public Commentaire getCommentaireById(Long id)
    {
        return this.commentaireRepository.findById(id).get();

    }

    /**
     * Méthode permet de sauvegarder le commentaire via ce service
     *
     * @param commentaire
     *
     */
    public Commentaire saveCommentaire(Commentaire commentaire)
    {

            return this.commentaireRepository.save(commentaire);
    }

    /**
     * Méthode permet de trouver le commentaire en fonction du site via ce service
     *
     * @param site1
     * * @return la liste des commentaires
     */
    public List<Commentaire> findCommentaireBySite(Site site1) throws UsernameNotFoundException {
        List<Commentaire>  commentaireTrouve = this.commentaireRepository.findBySite(site1);
        if (commentaireTrouve == null){
            throw new RuntimeException("Site introuvable");
        }
        return commentaireTrouve;
    }


    /**
     * Méthode permet d'effacer le commentaire en fonction de l'id via ce service
     *
     * @param id
     */
    public void deleteCommentaireById(Long id)
    {
           commentaireRepository.deleteById(id);
    }
}



