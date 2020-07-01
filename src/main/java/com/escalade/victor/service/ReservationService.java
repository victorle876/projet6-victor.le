package com.escalade.victor.service;

import com.escalade.victor.model.Reservation;
import com.escalade.victor.model.Topologie;
import com.escalade.victor.model.Utilisateur;
import com.escalade.victor.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    /**
     * Méthode permet de lister toutes les réservations via ce service
     *
     * * @return la liste des réservations
     */
    public List<Reservation> getAllReservations()
    {
        List<Reservation> ReservationList = reservationRepository.findAll();

        if(ReservationList.size() > 0) {
            return ReservationList;
        } else {
            return new ArrayList<Reservation>();
        }
    }

    /**
     * Méthode permet de consulter la reservation en fonction de l'id via ce service
     *
     * @param id
     * * @return la reservation via id
     */
    public Reservation getReservationById(Long id)
    {
        return this.reservationRepository.findById(id).get();

    }

    /**
     * Méthode permet de trouver la reservation en fonction du topo choisi via ce service
     *
     * @param topologieSelectionne
     * * @return la reservation en fonction du topo selectionne
     */
    public Reservation getReservationByTopologie(Topologie topologieSelectionne)
    {
        return this.reservationRepository.findByTopologie(topologieSelectionne).get();

    }

    /**
     * Méthode permet de sauvergarder
     *
     * @param reservation
     * * @return la reservation sauvée
     */
    public Reservation saveReservation(Reservation reservation)
    {
            return this.reservationRepository.save(reservation);
    }


    /**
     * Méthode permet de trouver la reservation en fonction de l'utilisateur via ce service
     *
     * @param utilisateur1
     * * @return la liste des réservations à partir de l'utilisateur
     */
    public List<Reservation> findReservationByUser(Utilisateur utilisateur1) throws UsernameNotFoundException {
        List<Reservation>  reservationTrouve = this.reservationRepository.findByUtilisateur(utilisateur1);
        if (reservationTrouve == null){
            throw new RuntimeException("reservation introuvable");
        }
        return reservationTrouve;
    }

    /**
     * Méthode permet de trouver la reservation en fonction de l'utilisateur propriétaire via ce service
     *
     * @param utilisateur1
     * * @return la liste des réservations à partir de l'utilisateur
     */
    public List<Reservation> findReservationByUserProprietaire(Utilisateur utilisateur1) throws UsernameNotFoundException {
        List<Reservation>  reservationTrouveByUserDifferent = this.reservationRepository.findByUtilisateurAndTopologie(utilisateur1);
        if (reservationTrouveByUserDifferent == null){
            throw new RuntimeException("reservation introuvable");
        }
        return reservationTrouveByUserDifferent;
    }

    /**
     * Méthode permet d'effacer le commentaire en fonction de l'id via ce service
     *
     * @param id
     */
    public void deleteReservationById(Long id)
    {
            reservationRepository.deleteById(id);
    }
}




