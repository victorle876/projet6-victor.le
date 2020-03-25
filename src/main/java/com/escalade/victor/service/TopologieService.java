package com.escalade.victor.service;

import com.escalade.victor.model.Site;
import com.escalade.victor.model.Topologie;
import com.escalade.victor.model.Utilisateur;
import com.escalade.victor.repository.TopologieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopologieService {

    @Autowired
    TopologieRepository topologieRepository;
    private List<Site> sites;

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

    //id topologie, Site site
    //.getSites.add(site)
    //OnetoOne

    public Topologie addTopoUtil (long id, Utilisateur utilisateur)
    {
        Topologie topologieUtil = this.getTopologieById(id);
        topologieUtil.setUtilisateur(utilisateur);
        return this.saveTopologie(topologieUtil);
    }

 /*   public Topologie addSiteTopo ( Site site)
    {

        topologieSite.getSites().add(site);
      //  topologie.setSites(sites);
        return this.saveTopologie(topologieSite);
    }*/
    public void deleteTopologiesById(Long id)
    {
/*        Optional<Topologie> TopologieEfface = topologieRepository.findById(id);

        if(TopologieEfface.isPresent())
        {*/
           topologieRepository.deleteById(id);
      //  }
    }
}

