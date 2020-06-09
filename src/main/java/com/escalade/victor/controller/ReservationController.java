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
    public String addTopoReservation(@PathVariable("id") Long id, Model model, Topologie topologie) {
        Topologie topologieId = this.topologieService.getTopologieById(id);
        model.addAttribute("id", id);
        //       model.addAttribute("topologiepublic",topologieDifferent);
        model.addAttribute("topologie", topologieId);
        model.addAttribute("reservation", new Reservation());
        //    return "listTopologieByUser";
        return "addTopoReservation";
    }

    @RequestMapping(value = "addTopoReservation/{id}", method = RequestMethod.POST)
    public String saveTopoReservation(@PathVariable("id") Long id, Model model, Topologie topologie, Reservation newReservation) {
        Topologie topologieId = this.topologieService.getTopologieById(id);
        System.out.println(topologieId);
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        //    Reservation reservation = this.reservationService.getReservationByTopologie(topologieId);
        //     if ((reservation.getTopologie().equals(topologieId.getId()) && (reservation.getEtat() != "En cours"))) {
        newReservation.setTopologie(topologieId);
        newReservation.setEtat("En cours");
        newReservation.setUtilisateur(utilisateurId);
        this.reservationService.saveReservation(newReservation);
        //     }
        model.addAttribute("reservationsbyuser", this.reservationService.getAllReservations());
        return "listReservationByUser";
        //   return "listTopologiePublic";
    }


    @RequestMapping(value = "/listReservationByUser", method = RequestMethod.GET)
    public String ReservationListByUser(Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        model.addAttribute("reservationsbyuser", this.reservationService.findReservationByUser(utilisateurId));
        return "listReservationByUser";

    }

    @RequestMapping(value = "/listValidationByUser", method = RequestMethod.GET)
    public String ValidationListByUser(Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        System.out.println(this.reservationService.findReservationByUserProprietaire(utilisateurId));
        model.addAttribute("reservationsbyuserdifferent", this.reservationService.findReservationByUserProprietaire(utilisateurId));
        return "listValidationByUser";

    }

    @RequestMapping(value = "/makeTopoAccepte/{id}", method = RequestMethod.GET)
    public String makeTopoAccepte(@PathVariable(value = "id") Long id, Model model) {
        Reservation reservationExistant = this.reservationService.getReservationById(id);
        model.addAttribute("reservation", this.reservationService.getReservationById(id));
        model.addAttribute("id", id);
        //     List<Reservation> reservationEnCours =  this.reservationService.findReservationByUserProprietaire(utilisateurId);
        //     model.addAttribute("reservationsbyuserdifferent", reservationEnCours);
        //    return "listValidationByUser";
        return "acceptReservation";
    }

    @RequestMapping(value = "/makeTopoAccepte/{id}", method = RequestMethod.POST)
    public String saveTopoAccepte(@PathVariable(value = "id") Long id, Reservation reservationExistant, Model model) {
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        reservationExistant = this.reservationService.getReservationById(id);
        Topologie topo = reservationExistant.getTopologie();
        System.out.println(topo);
        Utilisateur utilisateurProprietaire = topo.getUtilisateur();
        System.out.println(utilisateurProprietaire);
        System.out.println(reservationExistant.getEtat());
//        if (reservationExistant.getEtat() == "En cours") {
            //         model.addAttribute("isAccepted",Boolean.TRUE);
            reservationExistant.setEtat("Accepte");
            this.reservationService.saveReservation(reservationExistant);
            topo.setUtilisateur(reservationExistant.getUtilisateur());
            //        topo.setIspublic(Boolean.TRUE);
            this.topologieService.saveTopologie(topo);
//        }
        model.addAttribute("reservationsbyuserdifferent", this.reservationService.findReservationByUserProprietaire(utilisateurId));
        return "listValidationByUser";
    }

    @RequestMapping(value = "/makeTopoRefuse/{id}", method = RequestMethod.GET)
    public String makeTopoRefuse(@PathVariable(value = "id") Long id, Model model) {
 //       Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        Reservation reservationExistant = this.reservationService.getReservationById(id);
        model.addAttribute("reservation", this.reservationService.getReservationById(id));
        model.addAttribute("id", id);
 //       List<Reservation> reservationEnCours = this.reservationService.findReservationByUserProprietaire(utilisateurId);
  //      model.addAttribute("reservationbyuserdifferent", reservationEnCours);
        return "refusReservation";
    }

    @RequestMapping(value = "/makeTopoRefuse/{id}", method = RequestMethod.POST)
    public String saveTopoRefuse(@PathVariable(value = "id") Long id, Reservation reservationExistant, Model model) {
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        reservationExistant = this.reservationService.getReservationById(id);
        Topologie topo = reservationExistant.getTopologie();
        System.out.println(topo);
   //     if (reservationExistant.getEtat() == "En cours") {
   //         model.addAttribute("isBooked", Boolean.TRUE);
            reservationExistant.setEtat("Refusé");
            this.reservationService.saveReservation(reservationExistant);
  //      }
        model.addAttribute("reservationsbyuserdifferent", this.reservationService.findReservationByUser(utilisateurId));
        return "listValidationByUser";
    }

    @RequestMapping(value = "/makeTopoAnnule/{id}", method = RequestMethod.GET)
    public String makeTopoAnnule(@PathVariable(value = "id") Long id, Model model) {
        //      Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        Reservation reservationExistant = this.reservationService.getReservationById(id);
        model.addAttribute("reservation", this.reservationService.getReservationById(id));
        model.addAttribute("id", id);
        //      List<Reservation> reservationEnCours =  this.reservationService.findReservationByUser(utilisateurId);
        //     model.addAttribute("reservationsbyuser", reservationEnCours);
        //     return "listTopologieByUser";
        return "annulerReservation";
    }

    @RequestMapping(value = "/makeTopoAnnule/{id}", method = RequestMethod.POST)
    public String saveTopoAnnule(@PathVariable(value = "id") Long id, Reservation reservationExistant, Model model) {
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        reservationExistant = this.reservationService.getReservationById(id);
        Topologie topo = reservationExistant.getTopologie();
        System.out.println(topo);
        //     model.addAttribute("isBooked",Boolean.TRUE);
        reservationExistant.setEtat("Annulé");
        this.reservationService.saveReservation(reservationExistant);
        model.addAttribute("reservationsbyuser", this.reservationService.findReservationByUser(utilisateurId));
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
