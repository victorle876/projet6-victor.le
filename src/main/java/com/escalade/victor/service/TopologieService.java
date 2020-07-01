package com.escalade.victor.service;

import com.escalade.victor.exception.SiteIntrouvableException;
import com.escalade.victor.model.Site;
import com.escalade.victor.model.Topologie;
import com.escalade.victor.model.Utilisateur;
import com.escalade.victor.repository.SiteRepository;
import com.escalade.victor.repository.TopologieRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopologieService {

    @Autowired
    TopologieRepository topologieRepository;

    @Autowired
    SiteRepository siteRepository;

    private static final Logger logger = LogManager.getLogger(TopologieService.class);

    /**
     * Méthode permet de lister toutes les topologies via ce service
     *
     * * @return la liste des topos
     */
    public List<Topologie> getAllTopologies()
    {
        List<Topologie> TopologieList = topologieRepository.findAll();
        logger.debug(TopologieList.size());
        if(TopologieList.size() > 0) {
            return TopologieList;
        } else {
            return new ArrayList<Topologie>();
        }
    }

    /**
     * Méthode permet de consulter le topo en fonction de l'id via ce service
     *
     * @param id
     * * @return le topo via id
     */
    public Topologie getTopologieById(Long id)
    {
       return this.topologieRepository.findById(id).get();

    }

    /**
     * Méthode permet de sauvegarder le topo via ce service
     *
     * @param topologie
     * * @return le topo sauvegardé
     */
    public Topologie saveTopologie(Topologie topologie)
    {
        return this.topologieRepository.save(topologie);

    }

    /**
     * Méthode permet de trouver la liste des sites en fonction de l'utilisateur via ce service
     *
     * @param utilisateur1
     * @return la liste
     */
    public List<Site> findSiteByUser(Utilisateur utilisateur1) throws UsernameNotFoundException {
        List<Site>  siteTrouve = this.siteRepository.findByUtilisateurAndTopologieIsNull(utilisateur1);
        if (siteTrouve == null){
            throw new RuntimeException("Site introuvable");
        }
         return siteTrouve;
    }

    /**
     * Méthode permet de trouver la liste des topos en fonction de l'utilisateur via ce service
     *
     * @param utilisateur1
     * @return la liste
     */
    public List<Topologie> findTopologieByUser(Utilisateur utilisateur1) throws UsernameNotFoundException {
        List<Topologie>  topologieTrouve = this.topologieRepository.findByUtilisateur(utilisateur1);
        if (topologieTrouve == null){
            throw new RuntimeException("Topologie introuvable");
        }
        return topologieTrouve;
    }

    /**
     * Méthode permet de trouver la liste des topos en fonction de l'utilisateur autre  via ce service
     *
     * @param utilisateur1
     * @return la liste
     */
    public List<Topologie> findTopologieByUserDifferent(Utilisateur utilisateur1) throws UsernameNotFoundException {
        List<Topologie>  topologieTrouve = this.topologieRepository.findByUtilisateurNot(utilisateur1);
        if (topologieTrouve == null){
            throw new RuntimeException("Topologie introuvable");
        }
        return topologieTrouve;
    }

    /**
     * Méthode permet de trouver la liste des topos en fonction des critères de recherche via ce service
     *
     * @param recherche
     * @return la liste
     */
    public List<Topologie> findTopologieBySecteurOrNom(String recherche) throws UsernameNotFoundException {
        List<Topologie>  topologieTrouve = this.topologieRepository.findByNomTopologieIgnoreCaseContainingOrSecteurIgnoreCaseContaining(recherche, recherche);
        logger.debug(topologieTrouve);
        if (topologieTrouve == null){
            throw new RuntimeException("Topologie introuvable");
        }
        return topologieTrouve;
    }

    /**
     * Méthode permet de trouver la liste des topos en fonction du boolean Public via ce service
     *
     *
     * @return la liste
     */
    public List<Topologie> findTopologieByPublic() throws UsernameNotFoundException {
        List<Topologie>  topologiePublic = this.topologieRepository.findByIspublicTrue();
        logger.debug(topologiePublic);
        if (topologiePublic == null){
            throw new RuntimeException("Topologie introuvable");
        }
        return topologiePublic;
    }

    /**
     * Méthode permet de trouver la liste des topos publiques en fonction du boolean Public via ce service
     *
     * @param utilisateur1
     * @return la liste
     */
    public List<Topologie> findTopologieByPublicAndIspublic(Utilisateur utilisateur1) throws UsernameNotFoundException {
        List<Topologie>  topologiePublicUser = this.topologieRepository.findByAndIspublicTrueAndUtilisateurNot(utilisateur1);
        logger.debug(topologiePublicUser);
        if (topologiePublicUser == null){
            throw new RuntimeException("Topologie introuvable");
        }
        return topologiePublicUser;
    }

    /**
     * Méthode permet d'effacer le topo en fonction de l'id via ce service
     *
     * @param id
     */
    public void deleteTopologiesById(Long id)
    {
           topologieRepository.deleteById(id);
    }

}

