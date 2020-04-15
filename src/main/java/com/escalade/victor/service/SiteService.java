package com.escalade.victor.service;

import com.escalade.victor.model.*;
//import com.escalade.victor.model.Topologie;
import com.escalade.victor.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SiteService {

    @Autowired
    SiteRepository siteRepository;

    private Topologie topologieRecherche;

    public List<Site> getAllSites()
    {
        List<Site> SiteList = siteRepository.findAll();

        if(SiteList.size() > 0) {
            return SiteList;
        } else {
            return new ArrayList<Site>();
        }
    }

    public Site getSiteById(Long id)
    {
         return this.siteRepository.findById(id).get();

    }

    public Site saveSite(Site site)
    {
        return this.siteRepository.save(site);
    }

    public void deleteSiteById(Long id)
    {
            siteRepository.deleteById(id);
    //    }
    }

/*    public Site getSiteId (Utilisateur utilisateur1){
        return this.siteRepository.findByTopologieIsNull(utilisateur1).get();
    }*/

}

