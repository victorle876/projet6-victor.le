package com.escalade.victor.controller;

import com.escalade.victor.model.*;
import com.escalade.victor.repository.ReservationRepository;
import com.escalade.victor.repository.TopologieRepository;
import com.escalade.victor.service.ReservationService;
import com.escalade.victor.service.TopologieService;
import com.escalade.victor.service.UtilisateurService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(ReservationController.class);

    /**
     * Méthode permet de lister toutes les reservations
     *
     * @param model
     * * @return la page "listReservation"
     */
    @RequestMapping(value = "/listReservation", method = RequestMethod.GET)
    public String ReservationList(Model model) {
        model.addAttribute("reservations", this.reservationService.getAllReservations());
        return "listReservation";

    }

    /**
     * Méthode permet de voir la réservation en détail
     *
     * @param id
     * @param model
     * *  @return la page "detailsReservation"
     */
    @RequestMapping(value = "/detailsReservation", method = RequestMethod.GET)
    public String detail(@RequestParam(value = "id") Long id, Model model) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation == null) {
            logger.info("la reservation n'existe pas");
        }
        model.addAttribute("reservation", this.reservationService.getReservationById(id));
        return "detailsReservation";

    }


    /**
     * Méthode permet d'ajouter une reservation sur un topo en get
     *
     * @param id
     * @param model
     * @param topologie
     * *  @return la page "listTopologiePublic"
     */
    @RequestMapping(value = "addTopoReservation/{id}", method = RequestMethod.GET)
    public String addTopoReservation(@PathVariable("id") Long id, Model model, Topologie topologie) {
        Topologie topologieId = this.topologieService.getTopologieById(id);
        model.addAttribute("id", id);
        model.addAttribute("topologie", topologieId);
        model.addAttribute("reservation", new Reservation());
        return "listTopologiePublic" ;
    }

    /**
     * Méthode permet d'ajouter une reservation sur un topo en post
     *
     * @param id
     * @param model
     * @param topologie
     * *  @return la page "listRservationByUser"
     */
    @RequestMapping(value = "addTopoReservation/{id}", method = RequestMethod.POST)
    public String saveTopoReservation(@PathVariable("id") Long id, Model model, Topologie topologie, Reservation newReservation) {
        Topologie topologieId = this.topologieService.getTopologieById(id);
        logger.info(topologieId);
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        newReservation.setTopologie(topologieId);
        newReservation.setEtat("En cours");
        newReservation.setUtilisateur(utilisateurId);
        this.reservationService.saveReservation(newReservation);
        model.addAttribute("reservationsbyuserdifferent", this.reservationService.getAllReservations());
        return "redirect:/reservation/listReservationByUser";
    }

    /**
     * Méthode permet de lister les réservations de l'utilisateur concerné
     *
     * @param model
     * *  @return la page "listTopologiePublic"
     */
    @RequestMapping(value = "/listReservationByUser", method = RequestMethod.GET)
    public String ReservationListByUser(Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        model.addAttribute("reservationsbyuserdifferent", this.reservationService.findReservationByUser(utilisateurId));
        return "listReservationByUser";

    }

    /**
     * Méthode permet de lister les validations de l'utilisateur concerné
     *
     * @param model
     * *  @return la page "listValidationByUser"
     */
    @RequestMapping(value = "/listValidationByUser", method = RequestMethod.GET)
    public String ValidationListByUser(Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        logger.info(this.reservationService.findReservationByUserProprietaire(utilisateurId));
        model.addAttribute("reservationsbyuserdifferent", this.reservationService.findReservationByUserProprietaire(utilisateurId));
        return "listValidationByUser";

    }

    /**
     * Méthode permet à l'utilisateur propriétaire d'accepter en get
     *
     * @param model
     * *  @return la page "listValidationByUser"
     */
    @RequestMapping(value = "/makeTopoAccepte/{id}", method = RequestMethod.GET)
    public String makeTopoAccepte(@PathVariable(value = "id") Long id, Model model) {
        Reservation reservationExistant = this.reservationService.getReservationById(id);
        model.addAttribute("reservation", this.reservationService.getReservationById(id));
        model.addAttribute("id", id);
        return "listValidationByUser";
    }

    /**
     * Méthode permet à l'utilisateur propriétaire d'accepter en post
     *
     * @param model
     * @param reservationExistant
     * @param model
     * *  @return la page "listValidationByUser"
     */
    @RequestMapping(value = "/makeTopoAccepte/{id}", method = RequestMethod.POST)
    public String saveTopoAccepte(@PathVariable(value = "id") Long id, Reservation reservationExistant, Model model) {
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        reservationExistant = this.reservationService.getReservationById(id);
        Topologie topo = reservationExistant.getTopologie();
        Utilisateur utilisateurNew = reservationExistant.getUtilisateur();
        logger.info(topo);
        logger.info(reservationExistant.getEtat());
        reservationExistant.setEtat("Accepté");
        this.reservationService.saveReservation(reservationExistant);
        topo.setUtilisateur(utilisateurNew);
        topo.setIspublic(Boolean.FALSE);
        this.topologieService.saveTopologie(topo);
        model.addAttribute("reservationsbyuserdifferent", this.reservationService.findReservationByUserProprietaire(utilisateurId));
        return "redirect:/reservation/listValidationByUser";
    }

    /**
     * Méthode permet à l'utilisateur propriétaire de refuser en get
     *
     * @param model
     * @param id
     * *  @return la page "listValidationByUser"
     */
    @RequestMapping(value = "/makeTopoRefuse/{id}", method = RequestMethod.GET)
    public String makeTopoRefuse(@PathVariable(value = "id") Long id, Model model) {
 //       Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        Reservation reservationExistant = this.reservationService.getReservationById(id);
        model.addAttribute("reservation", this.reservationService.getReservationById(id));
        model.addAttribute("id", id);
        return "redirect:/reservation/listValidationByUser";
    }

    /**
     * Méthode permet à l'utilisateur propriétaire de refuser en post
     *
     * @param model
     * @param id
     * @param reservationExistant
     * *  @return la page "listValidationByUser"
     */
    @RequestMapping(value = "/makeTopoRefuse/{id}", method = RequestMethod.POST)
    public String saveTopoRefuse(@PathVariable(value = "id") Long id, Reservation reservationExistant, Model model) {
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        reservationExistant = this.reservationService.getReservationById(id);
        Topologie topo = reservationExistant.getTopologie();
        logger.info(topo);
        reservationExistant.setEtat("Refusé");
        this.reservationService.saveReservation(reservationExistant);
        model.addAttribute("reservationsbyuserdifferent", this.reservationService.findReservationByUser(utilisateurId));
        return "redirect:/reservation/listValidationByUser";
    }

    /**
     * Méthode permet au demandeur d'annuler la demande en get
     *
     * @param model
     * @param id
     * *  @return la page "listReservationByUser"
     */
    @RequestMapping(value = "/makeTopoAnnule/{id}", method = RequestMethod.GET)
    public String makeTopoAnnule(@PathVariable(value = "id") Long id, Model model) {
        //      Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        Reservation reservationExistant = this.reservationService.getReservationById(id);
        model.addAttribute("reservation", this.reservationService.getReservationById(id));
        model.addAttribute("id", id);
        return "listReservationByUser";
    }

    /**
     * Méthode permet au demandeur d'annuler la demande en post
     *
     * @param model
     * @param id
     * *  @return la page "listReservationByUser"
     */
    @RequestMapping(value = "/makeTopoAnnule/{id}", method = RequestMethod.POST)
    public String saveTopoAnnule(@PathVariable(value = "id") Long id, Reservation reservationExistant, Model model) {
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        reservationExistant = this.reservationService.getReservationById(id);
        Topologie topo = reservationExistant.getTopologie();
        logger.info(topo);
        reservationExistant.setEtat("Annulé");
        this.reservationService.saveReservation(reservationExistant);
        model.addAttribute("reservationsbyuserdifferent", this.reservationService.findReservationByUser(utilisateurId));
        return "redirect:/reservation/listReservationByUser";
    }

    /**
     * Méthode permet de modifier la réservation en get
     *
     * @param model
     * @param id
     * *  @return la page "editionReservation"
     */
    @RequestMapping(value = "/editionReservation", method = RequestMethod.GET)
    public String editionReservation(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("reservation", this.reservationService.getReservationById(id));
        return "editionReservation";

    }

    /**
     * Méthode permet de modifier la réservation en post
     *
     * @param model
     * @param id
     * @param Reservation
     * *  @return la page "editionReservation"
     */
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

    /**
     * Méthode permet d'effacer la réservation en get
     *
     * @param model
     * @param id
     * *  @return la page "editionReservation"
     */
    @RequestMapping(value = "/editionReservation1", method = RequestMethod.GET)
    public String editionReservation2(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("Reservation", this.reservationService.getReservationById(id));
        return "editionReservation";

    }

    /**
     * Méthode permet d'effacer la réservation en post
     *
     * @param model
     * @param id
     * @param reservation
     * @param errors
     * *  @return la page "editionReservation"
     */
    @RequestMapping(value = "/deleteReservation1", method = RequestMethod.POST)
    public String deleteReservation(@RequestParam(value = "id") long id, @Valid @ModelAttribute Reservation reservation, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editionReservation";
        } else {
            this.reservationService.deleteReservationById(reservation.getId());
            return "redirect:/";
        }
    }
}
