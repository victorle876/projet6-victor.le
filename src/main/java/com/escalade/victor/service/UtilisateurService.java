package com.escalade.victor.service;

import com.escalade.victor.model.Utilisateur;
import com.escalade.victor.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    UtilisateurRepository utilisateurRepository;

    public List<Utilisateur> getAllUsers()
    {
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();

        if(utilisateurList.size() > 0) {
            return utilisateurList;
        } else {
            return new ArrayList<Utilisateur>();
        }
    }

    public Utilisateur getUserById(Long id)
    {
        return this.utilisateurRepository.findById(id).get();

    }

    public Utilisateur saveUser(Utilisateur utilisateur)
    {
        Optional<Utilisateur> UtilisateurRecherche = utilisateurRepository.findById(utilisateur.getId());

            return this.utilisateurRepository.save(utilisateur);

    }

    public void deleteUserById(Long id)
    {
/*          Optional<Utilisateur> UtilisateurEfface = utilisateurRepository.findById(id);

          if(UtilisateurEfface.isPresent())
        {*/
           utilisateurRepository.deleteById(id);
       // }
    }
}
