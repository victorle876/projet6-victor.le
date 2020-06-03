package com.escalade.victor.controller;

import com.escalade.victor.model.*;
import com.escalade.victor.repository.ReservationRepository;
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

    @Autowired
    private ReservationRepository reservationRepository;


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

    @RequestMapping(value = "addTopoReservation/{id}", method = RequestMethod.GET)
    public String addTopoReservation(@PathVariable("id") Long id, Model model, Topologie topologie,@Valid @ModelAttribute Reservation newReservation) {
        Topologie topologieId = this.topologieService.getTopologieById(id);
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        List<Topologie> topologieDifferent =this.topologieService.findTopologieByPublicAndIspublic(utilisateurId);
        model.addAttribute("id",id);
        model.addAttribute("topologiepublic",topologieDifferent);
        model.addAttribute("topologieId", topologieId);
        model.addAttribute("reservation", new Reservation());
        return "listTopologiePublic";
    }

    @RequestMapping(value = "addTopoReservation/{id}", method = RequestMethod.POST)
    public String saveTopoReservation(@PathVariable("id") Long id, Model model, Topologie topologie,@Valid @ModelAttribute Reservation newReservation) {
        Topologie topologieId = this.topologieService.getTopologieById(id);
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        newReservation.setTopologie(topologieId);
        newReservation.setEtat("En cours");
        newReservation.setUtilisateur(utilisateurId);
        this.reservationService.saveReservation(newReservation);
        return "listTopologiePublic";
    }



    @RequestMapping(value = "/listReservationByUser", method = RequestMethod.GET)
    public String ReservationListByUser(Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        model.addAttribute("reservationsbyuser", this.reservationService.findReservationByUser(utilisateurId));
        return "listReservationByUser";

    }

    @RequestMapping(value = "/makeTopoAccepte/{id}", method = RequestMethod.GET)
    public String makeTopoAccepte(@PathVariable(value = "id") Long id, Reservation reservationExistant, Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        Topologie topo = this.topologieService.getTopologieById(id);
        model.addAttribute("topologie", this.topologieService.getTopologieById(id));
        model.addAttribute("id", id);
        List<Topologie> topologieEnCours =  this.topologieService.findTopologieByUser(utilisateurId);
        model.addAttribute("topologiesbyuser", topologieEnCours);
        model.addAttribute("topologie", topo);
        reservationExistant = this.reservationService.getReservationByTopologie(topo);
        System.out.println(reservationExistant);
        if (reservationExistant.getEtat() == "En cours") {
            model.addAttribute("isBooked",Boolean.TRUE);
            reservationExistant.setEtat("Accepte");
            this.reservationService.saveReservation(reservationExistant);
            topo.setUtilisateur(utilisateurId);
            topo.setIspublic(Boolean.TRUE);
            this.topologieService.saveTopologie(topo);
        }
        model.addAttribute("reservation", this.reservationService.findReservationByUser(utilisateurId));
        return "listTopologieByUser";
       // return "acceptReservation";
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
