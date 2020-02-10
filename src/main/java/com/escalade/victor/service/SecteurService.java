package com.escalade.victor.service;

import com.escalade.victor.model.Reservation;
import com.escalade.victor.model.Secteur;
import com.escalade.victor.repository.SecteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class SecteurService {

    @Autowired
    SecteurRepository secteurRepository;

    public List<Secteur> getAllSecteurs()
    {
        List<Secteur> SecteurList = secteurRepository.findAll();

        if(SecteurList.size() > 0) {
            return SecteurList;
        } else {
            return new ArrayList<Secteur>();
        }
    }

    public Secteur getSecteurById(Long id)
    {
        return this.secteurRepository.findById(id).get();

    }

    public Secteur saveSecteur(Secteur secteur)
    {
        return this.secteurRepository.save(secteur);

    }

    public void deleteSecteurById(Long id)
    {
  /*      Optional<Secteur> SecteurEfface = secteurRepository.findById(id);

        if(SecteurEfface.isPresent())
        {*/
            secteurRepository.deleteById(id);
  //      }
    }
}


