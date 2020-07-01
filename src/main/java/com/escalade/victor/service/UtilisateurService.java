package com.escalade.victor.service;

import com.escalade.victor.model.*;
import com.escalade.victor.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    UtilisateurRepository utilisateurRepository;

    /**
     * Méthode permet de lister tous les utilisateurs via ce service
     *
     * * @return la liste des utilisateurs
     */
    public List<Utilisateur> getAllUsers()
    {
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();

        if(utilisateurList.size() > 0) {
            return utilisateurList;
        } else {
            return new ArrayList<Utilisateur>();
        }
    }

    /**
     * Méthode permet de consulter l'utilisateur en fonction de l'id via ce service
     *
     * @param id
     * * @return l'utilisateur via id
     */
    public Utilisateur getUserById(Long id)
    {
        return this.utilisateurRepository.findById(id).get();
    }

    /**
     * Méthode permet de sauvegarder l'utilisateur via ce service
     *
     * @param utilisateur
     * * @return la voie sauvegardée
     */
    public Utilisateur saveUser(Utilisateur utilisateur)
    {
            return this.utilisateurRepository.save(utilisateur);
    }

    /**
     * Méthode permet d'effacer l'utilisateur en fonction de l'id via ce service
     *
     * @param id
     */
    public void deleteUserById(Long id)
    {
           utilisateurRepository.deleteById(id);
    }

    /**
     * Méthode permet de vérifier l'existence lors de la connexion via ce service
     *
     * @return l'utilisateur
     */
    public Utilisateur getUtilisateurConnected (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUtilisateur = authentication.getName();
       return this.utilisateurRepository.findByMail(emailUtilisateur).get();
    }
}
