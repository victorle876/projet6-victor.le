package com.escalade.victor.controller;

import com.escalade.victor.model.*;
import com.escalade.victor.repository.TopologieRepository;
import com.escalade.victor.service.ReservationService;
import com.escalade.victor.service.TopologieService;
import com.escalade.victor.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/reservation")
@Controller
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private TopologieService topologieService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private TopologieRepository topologieRepository;


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

/*    @RequestMapping(value = "/addReservation", method = RequestMethod.GET)
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
    }*/

    @RequestMapping(value = "addTopoReservation/{id}", method = RequestMethod.GET)
    public String addTopoReservation(@PathVariable("id") Long id, Model model, Topologie topologie) {
        Topologie topologieId = this.topologieService.getTopologieById(id);
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("topologieId", topologieId);
        return "addTopoReservation";
    }

    @RequestMapping(value = "addTopoReservation/{id}", method = RequestMethod.POST)
    public String saveTopoReservation(@PathVariable("id") Long id, Model model, Topologie topologie,@Valid @ModelAttribute Reservation newReservation, BindingResult result) {
        if (result.hasErrors()) {
            return "addTopoReservation";
        } else {
            Topologie topologieId = this.topologieService.getTopologieById(id);
            this.utilisateurService.getUtilisateurConnected();
            Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
            newReservation.setTopologie(topologieId);
            // newReservation.setAccepted(Boolean.FALSE);
            //newReservation.setEtat("En cours");
            newReservation.setUtilisateur(utilisateurId);
            this.reservationService.saveReservation(newReservation);
            model.addAttribute("reservations", this.reservationService.getAllReservations());
            return "listReservationByUser";
        }
    }

    @RequestMapping(value = "/listReservationByUser", method = RequestMethod.GET)
    public String ReservationListByUser(Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        model.addAttribute("reservationsbyuser", this.reservationService.findReservationByUser(utilisateurId));
        return "listReservationByUser";

    }

    @RequestMapping(value = "/makeTopoAccepte/{id}", method = RequestMethod.GET)
    public String makeTopoAccepte(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("idTopologie", id);
        model.addAttribute("topologie", this.topologieService.getTopologieById(id));
        return "acceptReservation";

    }

    @RequestMapping(value = "/makeTopoAccepte/{id}", method = RequestMethod.POST)
    public String saveTopoAccepte(@PathVariable(value = "id") Long id, Reservation reservationEnCours, Model model) {
        Topologie topo = this.topologieService.getTopologieById(id);
        reservationEnCours = this.reservationService.getReservationByTopologie(topo);
        //reservationEnCours.setEtat("ACCEPTEE");
        if (reservationEnCours.getEtat() == "ACCEPTEE") {
            this.utilisateurService.getUtilisateurConnected();
            Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
            topo.setUtilisateur(utilisateurId);
            this.topologieService.saveTopologie(topo);
            model.addAttribute("reservation", this.reservationService.findReservationByUser(utilisateurId));
        }
        return "listReservationByUser";

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
