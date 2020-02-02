package com.escalade.victor.service;

import com.escalade.victor.model.Secteur;
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
        Optional<Site> Site = SiteRepository.findById(id);

        if(Site.isPresent()) {
            Site existantSite= Site.get();
        }

    }

    public Site createOrUpdateSite(Site Site)
    {
        Optional<Site> SiteRecherche = SiteRepository.findById(Site.getId());

            Site nouvelSite = SiteRecherche.get();
            nouvelSite.setNomSite(Site.getNomSite());
            nouvelSite.setRegionSite(Site.getRegionSite());
            nouvelSite.setCreatedAt(Site.getCreatedAt());
            nouvelSite.setUpdatedAt(Site.getUpdatedAt());

            nouvelSite = SiteRepository.save(nouvelSite);

            return nouvelSite;

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

