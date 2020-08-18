package com.escalade.victor.service;

import com.escalade.victor.model.*;
//import com.escalade.victor.model.Topologie;
import com.escalade.victor.repository.SecteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecteurService {

    @Autowired
    SecteurRepository secteurRepository;

    /**
     * Méthode permet de lister tous les sites via ce service
     *
     * * @return la liste des sites
     */
    public List<Secteur> getAllSecteurs()
    {
        List<Secteur> SecteurList = secteurRepository.findAll();

        if(SecteurList.size() > 0) {
            return SecteurList;
        } else {
            return new ArrayList<Secteur>();
        }
    }

    /**
     * Méthode permet de consulter le site en fonction de l'id via ce service
     *
     * @param id
     * * @return le site via id
     */
    public Secteur getSecteurById(Long id)
    {
         return this.secteurRepository.findById(id).get();

    }

    /**
     * Méthode permet de sauvegarder le site via ce service
     *
     * @param secteur
     * * @return le site sauvegardé
     */
    public Secteur saveSecteur(Secteur secteur)
    {
        return this.secteurRepository.save(secteur);
    }

    /**
     * Méthode permet d'effacer le site en fonction de l'id via ce service
     *
     * @param id
     */
    public void deleteSecteurById(Long id)
    {
        secteurRepository.deleteById(id);
    }

    /**
     * Méthode permet de trouver la liste des sites en fonction de l'utilisateur via ce service
     *
     * @param utilisateur1
     * @return la liste
     */
    public List<Secteur> findSecteurByUser(Utilisateur utilisateur1) throws UsernameNotFoundException {
        List<Secteur>  secteurTrouve = this.secteurRepository.findByUtilisateur(utilisateur1);
        if (secteurTrouve == null){
            throw new RuntimeException("Secteur introuvable");
        }
        return secteurTrouve;
    }

    /**
     * Méthode permet de trouver la liste des sites en fonction de l'utilisateur via ce service
     *
     * @param recherche
     * @return la liste
     */
    public List<Secteur> findSecteurByNom(String recherche) throws UsernameNotFoundException {
        List<Secteur>  secteurTrouve = this.secteurRepository.findByNomSecteurIgnoreCaseContaining(recherche);
        if (secteurTrouve == null){
            throw new RuntimeException("Site introuvable");
        }
        return secteurTrouve;
    }
    public List<Secteur> findSecteurBySite(Site site1) throws UsernameNotFoundException {
        List<Secteur>  secteurTrouve = this.secteurRepository.findBySite(site1);
        if (secteurTrouve == null){
            throw new RuntimeException("Site introuvable");
        }
        return secteurTrouve;
    }

}

