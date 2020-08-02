package com.escalade.victor.service;

import com.escalade.victor.model.Site;
import com.escalade.victor.model.Topologie;
import com.escalade.victor.model.Utilisateur;
import com.escalade.victor.model.Voie;
import com.escalade.victor.repository.VoieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoieService {
    @Autowired
    VoieRepository voieRepository;

    private Site siteRecherche;

    /**
     * Méthode permet de lister toutes les voies via ce service
     *
     * * @return la liste des sites
     */
    public List<Voie> getAllVoies()
    {
        List<Voie> VoieList = voieRepository.findAll();

        if(VoieList.size() > 0) {
            return VoieList;
        } else {
            return new ArrayList<Voie>();
        }
    }

    /**
     * Méthode permet de consulter la voie en fonction de l'id via ce service
     *
     * @param id
     * * @return la voie via id
     */
    public Voie getVoieById(Long id)
    {
        return this.voieRepository.findById(id).get();
    }

    /**
     * Méthode permet de sauvegarder la voie via ce service
     *
     * @param Voie
     * * @return la voie sauvegardée
     */
    public Voie saveVoie(Voie Voie)
    {
        return this.voieRepository.save(Voie);
    }

    /**
     * Méthode permet d'effacer la voie en fonction de l'id via ce service
     *
     * @param id
     */
    public void deleteVoiesById(Long id)
    {
        voieRepository.deleteById(id);
    }

    /**
     * Méthode permet de trouver la liste des sites en fonction du site via ce service
     *
     * @param site1
     * @return la liste
     */
    public List<Voie> findVoieBySite(Site site1) throws UsernameNotFoundException {
        List<Voie>  voieTrouve = this.voieRepository.findBySite(site1);
        if (voieTrouve == null){
            throw new RuntimeException("Voie introuvable");
        }
        return voieTrouve;
    }

    /**
     * Méthode permet de trouver la liste des sites en fonction de l'utilisateur via ce service
     *
     * @param utilisateur1
     * @return la liste
     */
    public List<Voie> findVoieByUser(Utilisateur utilisateur1) throws UsernameNotFoundException {
        List<Voie>  voieTrouve = this.voieRepository.findByUtilisateur(utilisateur1);
        if (voieTrouve == null){
            throw new RuntimeException("Voie introuvable");
        }
        return voieTrouve;
    }

    public List<Voie> findVoieByNomOrCotation(String recherche) throws UsernameNotFoundException {
        List<Voie>  voieTrouve = this.voieRepository.findByNomVoieIgnoreCaseContainingOrCotationIgnoreCaseContaining(recherche, recherche);
        if (voieTrouve == null){
            throw new RuntimeException("Voie introuvable");
        }
        return voieTrouve;
    }
}
