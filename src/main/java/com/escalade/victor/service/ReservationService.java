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

    public List<Reservation> getAllReservations()
    {
        List<Reservation> ReservationList = reservationRepository.findAll();

        if(ReservationList.size() > 0) {
            return ReservationList;
        } else {
            return new ArrayList<Reservation>();
        }
    }

    public Reservation getReservationById(Long id)
    {
        return this.reservationRepository.findById(id).get();

    }

    public Reservation getReservationByTopologie(Topologie topologieSelectionne)
    {
        return this.reservationRepository.findByTopologie(topologieSelectionne).get();

    }

    public Reservation saveReservation(Reservation reservation)
    {
            return this.reservationRepository.save(reservation);
    }

/*    public Reservation addTopoReserv (long id, Topologie topologie)
    {
        Reservation reservationTopo = this.getReservationById(id);
        reservationTopo.setTopologie(topologie);
        return this.saveReservation(reservationTopo);
    }*/

    public List<Reservation> findReservationByUser(Utilisateur utilisateur1) throws UsernameNotFoundException {
        List<Reservation>  reservationTrouve = this.reservationRepository.findByUtilisateur(utilisateur1);
        if (reservationTrouve == null){
            throw new RuntimeException("reservation introuvable");
        }
        return reservationTrouve;
    }
    
    public void deleteReservationById(Long id)
    {
            reservationRepository.deleteById(id);
    }
}




