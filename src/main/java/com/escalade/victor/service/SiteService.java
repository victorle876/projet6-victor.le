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
    SiteRepository siteRepository;

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
/*        Optional<Site> SiteEfface = siteRepository.findById(id);

        if(SiteEfface.isPresent())
        {*/
            siteRepository.deleteById(id);
    //    }
    }
}

