package com.escalade.victor.service;

import com.escalade.victor.model.*;
import com.escalade.victor.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    //       Optional<Utilisateur> UtilisateurRecherche = utilisateurRepository.findById(utilisateur.getId());


            return this.utilisateurRepository.save(utilisateur);

    }

    public Utilisateur addUtilCommentaire (long id, List<Commentaire> commentaires, Commentaire commentaire)
    {
        Utilisateur utilisateurComm = this.getUserById(id);
        utilisateurComm.getCommentaires().add(commentaire);
        //  topologie.setSites(sites);
        return this.saveUser(utilisateurComm);
    }

    public Utilisateur addUtilReservation (long id, List<Reservation> reservations, Reservation reservation)
    {
        Utilisateur utilisateurReserv = this.getUserById(id);
        utilisateurReserv.getReservations().add(reservation);
        //  topologie.setSites(sites);
        return this.saveUser(utilisateurReserv);
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
