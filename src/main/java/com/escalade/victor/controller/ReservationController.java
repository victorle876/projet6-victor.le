package com.escalade.victor.controller;

import com.escalade.victor.model.Reservation;
import com.escalade.victor.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@RequestMapping("/reservation")
@Controller
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String reservationHome(Model model) {
        return "reservationhome";
    }

    @RequestMapping(value = "/listReservation", method = RequestMethod.GET)
    public String ReservationList(Model model) {
        model.addAttribute("reservations", this.reservationService.getAllReservations());
        return "listReservation";

    }


    @RequestMapping(value = "/detailsReservation", method = RequestMethod.GET)
    public String detail(@RequestParam(value = "id") Long id, Model model) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation == null) {
            System.out.println("le Reservation n'existe pas");
        }
        model.addAttribute("reservation", this.reservationService.getReservationById(id));
        return "detailsReservation";

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
            model.addAttribute("reservations", this.reservationService.getAllReservations());
            return "home";
        }
    }

    @RequestMapping(value = "/editionReservation", method = RequestMethod.GET)
    public String editionReservation(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("reservation", this.reservationService.getReservationById(id));
        return "editionReservation";

    }

    @RequestMapping(value = "/editionReservation", method = RequestMethod.POST)
    public String editionReservation(@RequestParam(value = "id") long id, @Valid @ModelAttribute Reservation Reservation, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editionReservation";
        } else {
            this.reservationService.saveReservation(Reservation);
            model.addAttribute("Reservations", this.reservationService.getAllReservations());
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/editionReservation1", method = RequestMethod.GET)
    public String editionReservation2(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("Reservation", this.reservationService.getReservationById(id));
        return "editionReservation";

    }

    @RequestMapping(value = "/deleteReservation1", method = RequestMethod.POST)
    public String deleteReservation(@RequestParam(value = "id") long id, @Valid @ModelAttribute Reservation reservation, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editionReservation";
        } else {
            this.reservationService.deleteReservationById(reservation.getId());
    //        model.addAttribute("Reservations", this.reservationService.deleteReservationById(id));
            return "redirect:/";
        }
    }
}
