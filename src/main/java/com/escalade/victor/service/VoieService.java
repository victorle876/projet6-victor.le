package com.escalade.victor.service;

import com.escalade.victor.model.Voie;
import com.escalade.victor.repository.VoieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoieService {
    @Autowired
    VoieRepository voieRepository;

    public List<Voie> getAllVoies()
    {
        List<Voie> VoieList = voieRepository.findAll();

        if(VoieList.size() > 0) {
            return VoieList;
        } else {
            return new ArrayList<Voie>();
        }
    }

    public Voie getVoieById(Long id)
    {
        return this.voieRepository.findById(id).get();

    }

    public Voie saveVoie(Voie Voie)
    {
        return this.voieRepository.save(Voie);

    }

    public void deleteVoiesById(Long id)
    {
/*        Optional<Voie> VoieEfface = VoieRepository.findById(id);

        if(VoieEfface.isPresent())
        {*/
        voieRepository.deleteById(id);
        //  }
    }
}