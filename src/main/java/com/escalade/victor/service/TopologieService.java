package com.escalade.victor.service;

import com.escalade.victor.exception.SiteIntrouvableException;
import com.escalade.victor.model.Site;
import com.escalade.victor.model.Topologie;
import com.escalade.victor.model.Utilisateur;
import com.escalade.victor.repository.SiteRepository;
import com.escalade.victor.repository.TopologieRepository;
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


    public List<Topologie> getAllTopologies()
    {
        List<Topologie> TopologieList = topologieRepository.findAll();

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
        List<Topologie>  topologieTrouve = this.topologieRepository.findTopologieByUtilisateur(utilisateur1);
        if (topologieTrouve == null){
            throw new RuntimeException("Topologie introuvable");
        }
        return topologieTrouve;
    }


    public void deleteTopologiesById(Long id)
    {
           topologieRepository.deleteById(id);

    }

}

