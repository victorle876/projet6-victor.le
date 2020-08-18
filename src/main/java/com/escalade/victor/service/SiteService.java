package com.escalade.victor.service;

import com.escalade.victor.model.Site;
import com.escalade.victor.model.Utilisateur;
import com.escalade.victor.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SiteService {

    @Autowired
    SiteRepository siteRepository;

    /**
     * Méthode permet de lister tous les sites via ce service
     *
     * * @return la liste des sites
     */
    public List<Site> getAllSites()
    {
        List<Site> SiteList = siteRepository.findAll();

        if(SiteList.size() > 0) {
            return SiteList;
        } else {
            return new ArrayList<Site>();
        }
    }

    /**
     * Méthode permet de consulter le site en fonction de l'id via ce service
     *
     * @param id
     * * @return le site via id
     */
    public Site getSiteById(Long id)
    {
         return this.siteRepository.findById(id).get();

    }

    /**
     * Méthode permet de sauvegarder le site via ce service
     *
     * @param site
     * * @return le site sauvegardé
     */
    public Site saveSite(Site site)
    {
        return this.siteRepository.save(site);
    }

    /**
     * Méthode permet d'effacer le site en fonction de l'id via ce service
     *
     * @param id
     */
    public void deleteSiteById(Long id)
    {
        siteRepository.deleteById(id);
    }

    /**
     * Méthode permet de trouver la liste des sites en fonction de l'utilisateur via ce service
     *
     * @param utilisateur1
     * @return la liste
     */
    public List<Site> findSiteByUser(Utilisateur utilisateur1) throws UsernameNotFoundException {
        List<Site> siteTrouve = this.siteRepository.findByUtilisateur(utilisateur1);
        if (siteTrouve == null){
            throw new RuntimeException("Site introuvable");
        }
        return siteTrouve;
    }

    public List<Site> findSiteByNom(String recherche) throws UsernameNotFoundException {
        List<Site>  siteTrouve = this.siteRepository.findByNomSiteIgnoreCaseContainingOrNombreSecteurIgnoreCaseContainingOrPaysIgnoreCaseContaining(recherche,recherche,recherche);
        if (siteTrouve == null){
            throw new RuntimeException("Site introuvable");
        }
        return siteTrouve;
    }

}

