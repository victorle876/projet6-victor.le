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

    public List<Site> findSiteByAll(String recherche,String recherche1, String recherche2) throws UsernameNotFoundException {
        List<Site>  siteTrouveByAll = this.siteRepository.findByNomSiteIgnoreCaseContainingOrNombreSecteurIgnoreCaseContainingOrPaysIgnoreCaseContaining(recherche,recherche1,recherche2);
        if (siteTrouveByAll == null){
            throw new RuntimeException("Site introuvable");
        }
        return siteTrouveByAll;
    }

    public List<Site> findSiteByNombreSecteur(String recherche1) throws UsernameNotFoundException {
        List<Site>  siteTrouveByNombreSecteur = this.siteRepository.findByNombreSecteurIgnoreCaseContaining(recherche1);
        if (siteTrouveByNombreSecteur == null){
            throw new RuntimeException("Site introuvable");
        }
        return siteTrouveByNombreSecteur;
    }

    public List<Site> findSiteByPays(String recherche2) throws UsernameNotFoundException {
        List<Site>  siteTrouveByPays = this.siteRepository.findByPaysIgnoreCaseContaining(recherche2);
        if (siteTrouveByPays == null){
            throw new RuntimeException("Site introuvable");
        }
        return siteTrouveByPays;
    }

    public List<Site> findSiteByNombreSecteurAndPays(String recherche1, String recherche2) throws UsernameNotFoundException {
        List<Site>  siteTrouve = this.siteRepository.findByNombreSecteurIgnoreCaseContainingAndPaysIgnoreCaseContaining(recherche1,recherche2);
        if (siteTrouve == null){
            throw new RuntimeException("Site introuvable");
        }
        return siteTrouve;
    }

    public List<Site> findSiteByNom(String recherche) throws UsernameNotFoundException {
        List<Site>  siteTrouveByNom = this.siteRepository.findByNomSiteIsContaining(recherche);
        System.out.println("ici2");
        if (siteTrouveByNom == null){
            throw new RuntimeException("Site introuvable");
        }
        return siteTrouveByNom;
    }

}

