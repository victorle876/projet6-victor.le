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

    public Topologie getTopologieById(Long id)
    {
       return this.topologieRepository.findById(id).get();

    }

    public Topologie saveTopologie(Topologie topologie)
    {
        return this.topologieRepository.save(topologie);

    }

    public List<Site> findSiteByUser(Utilisateur utilisateur1) throws UsernameNotFoundException {
        List<Site>  siteTrouve = this.siteRepository.findByUtilisateurAndTopologieIsNull(utilisateur1);
        if (siteTrouve == null){
            throw new RuntimeException("Site introuvable");
        }
         return siteTrouve;
    }

    public List<Topologie> findTopologieByUser(Utilisateur utilisateur1) throws UsernameNotFoundException {
        List<Topologie>  topologieTrouve = this.topologieRepository.findByUtilisateur(utilisateur1);
        if (topologieTrouve == null){
            throw new RuntimeException("Topologie introuvable");
        }
        return topologieTrouve;
    }

    public List<Topologie> findTopologieByUserDifferent(Utilisateur utilisateur1) throws UsernameNotFoundException {
        List<Topologie>  topologieTrouve = this.topologieRepository.findByUtilisateurNot(utilisateur1);
        if (topologieTrouve == null){
            throw new RuntimeException("Topologie introuvable");
        }
        return topologieTrouve;
    }

    public List<Topologie> findTopologieBySecteurOrNom(String recherche) throws UsernameNotFoundException {
        List<Topologie>  topologieTrouve = this.topologieRepository.findByNomTopologieIgnoreCaseContainingOrSecteurIgnoreCaseContaining(recherche, recherche);
        logger.debug(topologieTrouve);
        if (topologieTrouve == null){
            throw new RuntimeException("Topologie introuvable");
        }
        return topologieTrouve;
    }

    public List<Topologie> findTopologieByPublic() throws UsernameNotFoundException {
        List<Topologie>  topologiePublic = this.topologieRepository.findByIspublicTrue();
        logger.debug(topologiePublic);
        if (topologiePublic == null){
            throw new RuntimeException("Topologie introuvable");
        }
        return topologiePublic;
    }

    public List<Topologie> findTopologieByPublicAndIspublic(Utilisateur utilisateur1) throws UsernameNotFoundException {
        List<Topologie>  topologiePublicUser = this.topologieRepository.findByAndIspublicTrueAndUtilisateurNot(utilisateur1);
        logger.debug(topologiePublicUser);
        if (topologiePublicUser == null){
            throw new RuntimeException("Topologie introuvable");
        }
        return topologiePublicUser;
    }


    public void deleteTopologiesById(Long id)
    {
           topologieRepository.deleteById(id);

    }

}

