package com.escalade.victor.service;

import com.escalade.victor.model.Topologie;
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

    public void getTopologieById(Long id)
    {
        Optional<Topologie> Topologie1 = topologieRepository.findById(id);

        if(Topologie1.isPresent()) {
            return Topologie1.get();
        }

    }

    public Topologie createOrUpdateTopologie(Topologie Topologie)
    {
        Optional<Topologie> TopologieRecherche = topologieRepository.findById(Topologie.getId());

        if(TopologieRecherche.isPresent())
        {
            Topologie nouvelTopologie = TopologieRecherche.get();
            nouvelTopologie.setAuteur(Topologie.getAuteur());
            nouvelTopologie.setNombre_page(Topologie.getNombre_page());
            nouvelTopologie.setUpdatedAt(Topologie.getUpdatedAt());
            nouvelTopologie.setCreatedAt(Topologie.getCreatedAt());

            nouvelTopologie = topologieRepository.save(nouvelTopologie);

            return nouvelTopologie;
        } else {
            Topologie = topologieRepository.save(Topologie);

            return Topologie;
        }
    }

    public void deleteTopologiesById(Long id)
    {
        Optional<Topologie> TopologieEfface = topologieRepository.findById(id);

        if(TopologieEfface.isPresent())
        {
            topologieRepository.deleteById(id);
        }
    }
}

