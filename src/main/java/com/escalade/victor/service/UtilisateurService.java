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

    public Utilisateur getUserById(Long id)
    {
        Optional<Utilisateur> utilisateur1 = UtilisateurRepository.findById(id);

        if(utilisateur1.isPresent()) {
            return utilisateur1.get();
        }
    }

    public Utilisateur createOrUpdateUser(Utilisateur utilisateur)
    {
        Optional<Utilisateur> UtilisateurRecherche = UtilisateurRepository.findById(utilisateur.getId());

        if(UtilisateurRecherche.isPresent())
        {
            Utilisateur nouvelUtilisateur = UtilisateurRecherche.get();
            nouvelUtilisateur.setMail(utilisateur.getMail());
            nouvelUtilisateur.setUsername(utilisateur.getUsername());
            nouvelUtilisateur.setPrenom(utilisateur.getPrenom());
            nouvelUtilisateur.setPassword(utilisateur.getPassword());
            nouvelUtilisateur.setDateNaissance(utilisateur.getDateNaissance());
            nouvelUtilisateur.setRoles(utilisateur.getRoles());

            nouvelUtilisateur = UtilisateurRepository.save(nouvelUtilisateur);

            return nouvelUtilisateur;
        } else {
            utilisateur = UtilisateurRepository.save(utilisateur);

            return utilisateur;
        }
    }

    public void deleteEmployeeById(Long id)
    {
        Optional<Utilisateur> UtilisateurEfface = UtilisateurRepository.findById(id);

        if(UtilisateurEfface.isPresent())
        {
            UtilisateurRepository.deleteById(id);
        }
    }
}
