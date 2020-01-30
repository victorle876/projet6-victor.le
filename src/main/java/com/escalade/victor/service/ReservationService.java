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
    ReservationRepository ReservationRepository;

    public List<Reservation> getAllReservations()
    {
        List<Reservation> ReservationList = ReservationRepository.findAll();

        if(ReservationList.size() > 0) {
            return ReservationList;
        } else {
            return new ArrayList<Reservation>();
        }
    }

    public void getReservationById(Long id)
    {
        Optional<Reservation> Reservation = ReservationRepository.findById(id);

        if(Reservation.isPresent()) {

            Reservation existantReservation= Reservation.get();
        }

    }

    public Reservation createOrUpdateReservation(Reservation Reservation)
    {
        Optional<Reservation> ReservationRecherche = ReservationRepository.findById(Reservation.getId());

        if(ReservationRecherche.isPresent())
        {
            Reservation nouvelReservation = ReservationRecherche.get();
/*            nouvelReservation.setDateDebut(Reservation.getDateDebut());
            nouvelReservation.setDateFin(Reservation.getDateFin());*/
            nouvelReservation.setDuree(Reservation.getDuree());

 //           nouvelReservation = ReservationRepository.save(nouvelReservation);

            return nouvelReservation;
        } else {
            Reservation = ReservationRepository.save(Reservation);

            return Reservation;
        }
    }

    public void deleteReservationById(Long id)
    {
        Optional<Reservation> ReservationEfface = ReservationRepository.findById(id);

        if(ReservationEfface.isPresent())
        {
            ReservationRepository.deleteById(id);
        }
    }
}




