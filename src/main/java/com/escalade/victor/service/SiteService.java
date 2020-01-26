package com.escalade.victor.service;

import com.escalade.victor.model.Site;
import com.escalade.victor.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SiteService {

    @Autowired
    SiteRepository SiteRepository;

    public List<Site> getAllSites()
    {
        List<Site> SiteList = SiteRepository.findAll();

        if(SiteList.size() > 0) {
            return SiteList;
        } else {
            return new ArrayList<Site>();
        }
    }

    public void getSiteById(Long id)
    {
        Optional<Site> Site1 = SiteRepository.findById(id);

        if(Site1.isPresent()) {
            return Site1.get();
        }

    }

    public Site createOrUpdateSite(Site Site)
    {
        Optional<Site> SiteRecherche = SiteRepository.findById(Site.getId());

        if(SiteRecherche.isPresent())
        {
            Site nouvelSite = SiteRecherche.get();
            nouvelSite.setNom_site(Site.getNom_site());
            nouvelSite.setRegion_site(Site.getRegion_site());
            nouvelSite.setCreatedAt(Site.getCreatedAt());
            nouvelSite.setUpdatedAt(Site.getUpdatedAt());

            nouvelSite = SiteRepository.save(nouvelSite);

            return nouvelSite;
        } else {
            Site = SiteRepository.save(Site);

            return Site;
        }
    }

    public void deleteSiteById(Long id)
    {
        Optional<Site> SiteEfface = SiteRepository.findById(id);

        if(SiteEfface.isPresent())
        {
            SiteRepository.deleteById(id);
        }
    }
}

