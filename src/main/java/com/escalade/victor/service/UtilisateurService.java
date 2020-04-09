package com.escalade.victor.service;

import com.escalade.victor.model.*;
import com.escalade.victor.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
            return this.utilisateurRepository.save(utilisateur);
    }

    public void deleteUserById(Long id)
    {
           utilisateurRepository.deleteById(id);
    }

    public Utilisateur getUtilisateurConnected (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUtilisateur = authentication.getName();
       return this.utilisateurRepository.findByMail(emailUtilisateur).get();
      //  return this.utilisateurRepository.findByMail(emailUtilisateur).orElseGet(null);
    }
}
