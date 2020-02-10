package com.escalade.victor.service;

import com.escalade.victor.model.Reservation;
import com.escalade.victor.model.Utilisateur;
import com.escalade.victor.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Reservation saveReservation(Reservation reservation)
    {
            return this.reservationRepository.save(reservation);
    }

    public void deleteReservationById(Long id)
    {
/*        Optional<Reservation> ReservationEfface = reservationRepository.findById(id);

        if(ReservationEfface.isPresent())
        {*/
            reservationRepository.deleteById(id);
   //     }
    }
}




