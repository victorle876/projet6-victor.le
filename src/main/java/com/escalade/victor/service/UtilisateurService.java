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
    UtilisateurRepository UtilisateurRepository;

    public List<Utilisateur> getAllUsers()
    {
        List<Utilisateur> utilisateurList = UtilisateurRepository.findAll();

        if(utilisateurList.size() > 0) {
            return utilisateurList;
        } else {
            return new ArrayList<Utilisateur>();
        }
    }

    public void getUserById(Long id)
    {
        Optional<Utilisateur> utilisateur = UtilisateurRepository.findById(id);

        if(utilisateur.isPresent()) {

            Utilisateur existantUtilisateur = utilisateur.get();
        }

    }

    public Utilisateur createOrUpdateUser(Utilisateur utilisateur)
    {
        Optional<Utilisateur> UtilisateurRecherche = UtilisateurRepository.findById(utilisateur.getId());

        if(UtilisateurRecherche.isPresent())
        {
            Utilisateur existantUtilisateur = UtilisateurRecherche.get();
/*            nouvelUtilisateur.setMail(utilisateur.getMail());
            nouvelUtilisateur.setUsername(utilisateur.getUsername());
            nouvelUtilisateur.setPrenom(utilisateur.getPrenom());
            nouvelUtilisateur.setPassword(utilisateur.getPassword());
            nouvelUtilisateur.setDateNaissance(utilisateur.getDateNaissance());
            nouvelUtilisateur.setRoles(utilisateur.getRoles());*/

            existantUtilisateur= UtilisateurRepository.save(existantUtilisateur);
            return existantUtilisateur;

        } else {
            utilisateur = UtilisateurRepository.save(utilisateur);

            return utilisateur;
        }
    }

    public void deleteUserById(Long id)
    {
        Optional<Utilisateur> UtilisateurEfface = UtilisateurRepository.findById(id);

        if(UtilisateurEfface.isPresent())
        {
            UtilisateurRepository.deleteById(id);
        }
    }
}
