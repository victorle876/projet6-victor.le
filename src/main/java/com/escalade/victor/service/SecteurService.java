package com.escalade.victor.service;

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
    SecteurRepository SecteurRepository;

    public List<Secteur> getAllSecteurs()
    {
        List<Secteur> SecteurList = SecteurRepository.findAll();

        if(SecteurList.size() > 0) {
            return SecteurList;
        } else {
            return new ArrayList<Secteur>();
        }
    }

    public void getSecteurById(Long id)
    {
        Optional<Secteur> Secteur = SecteurRepository.findById(id);

        if(Secteur.isPresent()) {
            return Secteur.get();

        }

    }

    public Secteur createOrUpdateSecteur(Secteur Secteur)
    {
        Optional<Secteur> SecteurRecherche = SecteurRepository.findById(Secteur.getId());

        if(SecteurRecherche.isPresent())
        {
            Secteur nouvelSecteur = SecteurRecherche.get();
/*            nouvelSecteur.setNom_Secteur(Secteur.getNom_Secteur());
            nouvelSecteur.setHauteur(Secteur.getHauteur());
            nouvelSecteur.setCotation(Secteur.getCotation());
            nouvelSecteur.setLongueur(Secteur.getLongueur());
            nouvelSecteur.setCreatedAt(Secteur.getCreatedAt());
            nouvelSecteur.setUpdatedAt(Secteur.getUpdatedAt());*/

            nouvelSecteur = SecteurRepository.save(nouvelSecteur);

            return nouvelSecteur;
        } else {
            Secteur = SecteurRepository.save(Secteur);

            return Secteur;
        }
    }

    public void deleteSecteurById(Long id)
    {
        Optional<Secteur> SecteurEfface = SecteurRepository.findById(id);

        if(SecteurEfface.isPresent())
        {
            SecteurRepository.deleteById(id);
        }
    }
}


