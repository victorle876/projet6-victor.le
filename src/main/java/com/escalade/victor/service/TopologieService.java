package com.escalade.victor.service;

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

    public void deleteTopologiesById(Long id)
    {
/*        Optional<Topologie> TopologieEfface = topologieRepository.findById(id);

        if(TopologieEfface.isPresent())
        {*/
           topologieRepository.deleteById(id);
      //  }
    }
}

