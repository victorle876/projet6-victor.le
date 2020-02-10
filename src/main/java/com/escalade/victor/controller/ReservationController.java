package com.escalade.victor.controller;

import com.escalade.victor.model.Reservation;
import com.escalade.victor.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/detailsReservation", method = RequestMethod.GET)
    public String detail(@RequestParam(value = "id") Long id, Model model) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation == null) {
            System.out.println("le Reservation n'existe pas");
        }
        model.addAttribute("reservation", this.reservationService.getReservationById(id));
        return "detailReservation";

    }

    @RequestMapping(value = "/addReservation", method = RequestMethod.GET)
    public String ajouterReservation(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "addReservation";
    }

    @RequestMapping(value = "/saveReservation", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute Reservation reservation, Model model, BindingResult result) {

        if (result.hasErrors()) {
            return "addReservation";
        } else {
            this.reservationService.saveReservation(reservation);
            model.addAttribute("Reservations", this.reservationService.getAllReservations());
            return "home";
        }
    }

    @RequestMapping(value = "/editionReservation", method = RequestMethod.GET)
    public String editionReservation(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("Reservation", this.reservationService.getReservationById(id));
        return "editUser";

    }

    @RequestMapping(value = "/editionReservation", method = RequestMethod.POST)
    public String editionReservation(@RequestParam(value = "id") long id, @Valid @ModelAttribute Reservation Reservation, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editReservation";
        } else {
            this.reservationService.saveReservation(Reservation);
            model.addAttribute("Reservations", this.reservationService.getAllReservations());
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/editionReservation1", method = RequestMethod.GET)
    public String editionReservation2(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("Reservation", this.reservationService.getReservationById(id));
        return "editReservation";

    }

    @RequestMapping(value = "/deleteReservation1", method = RequestMethod.POST)
    public String deleteReservation(@RequestParam(value = "id") long id, @Valid @ModelAttribute Reservation reservation, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editReservation";
        } else {
            this.reservationService.deleteReservationById(reservation.getId());
    //        model.addAttribute("Reservations", this.reservationService.deleteReservationById(id));
            return "redirect:/";
        }
    }
}
