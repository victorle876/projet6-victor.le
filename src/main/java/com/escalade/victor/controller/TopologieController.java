package com.escalade.victor.controller;

import com.escalade.victor.model.*;
import com.escalade.victor.repository.SiteRepository;
import com.escalade.victor.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/topologie")
@Controller
public class TopologieController {

    @Autowired
    private TopologieService topologieService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private VoieService voieService;

    @Autowired
    private SiteRepository siteRepository;

    private static final Logger logger = LogManager.getLogger(TopologieController.class);

    /**
     * Méthode permet de lister toutes les topologies
     *
     * @param model
     * * @return la page "listTopologie"
     */
    @RequestMapping(value = "/listTopologie", method = RequestMethod.GET)
    public String TopoList(Model model) {
        model.addAttribute("topologies", this.topologieService.getAllTopologies());
        return "listTopologie";
    }

    /**
     * Méthode permet de voir en detail la topologie
     *
     * @param model
     * @param id
     * * @return la page "detailsTopologie"
     */
    @RequestMapping(value = "/detailsTopologie", method = RequestMethod.GET)
    public String detail(@RequestParam(value = "id") Long id, Model model) {
        Topologie topologie = topologieService.getTopologieById(id);
        if (topologie == null) {
            logger.debug("le topologie n'existe pas");
        }
        logger.info(this.topologieService.getTopologieById(id));
        model.addAttribute("topologie", this.topologieService.getTopologieById(id));
        return "detailsTopologie";

    }

    /**
     * Méthode permet d'ajouter le topologie en get
     *
     * @param model
     * * @return la page "addTopologie"
     */
    @RequestMapping(value = "/addTopologie", method = RequestMethod.GET)
    public String ajouterTopologie(Model model) {
        model.addAttribute("topologie", new Topologie());
        return "addTopologie";
    }

    /**
     * Méthode permet d'ajouter le topologie en post
     *
     * @param model
     * @param topologie
     * @param result
     * * @return la page "listTopologie"
     */
    @RequestMapping(value = "/saveTopologie", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute Topologie topologie, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "addTopologie";
        } else {
            this.utilisateurService.getUtilisateurConnected();
            Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
            topologie.setUtilisateur(utilisateurId);
            logger.info("utilisateurId");
            topologie.setIspublic(Boolean.FALSE); // mise à flag false pour topo non publié
            this.topologieService.saveTopologie(topologie);
            model.addAttribute("topologies", this.topologieService.findTopologieByUser(utilisateurId));
            return "redirect:/topologie/listTopologieByUser";
        }
    }

    /**
     * Méthode permet de rattacher le topo sur le site en get
     *
     * @param model
     * @param site
     * @param id
     * * @return la page "addSiteTopo"
     */
    @RequestMapping(value = "addSiteTopo/{id}", method = RequestMethod.GET)
    public String addSiteTopo(@PathVariable("id") Long id, Model model, Site site) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        List<Site> siteTrouve = this.topologieService.findSiteByUser(utilisateurId);
        Site siteRequis = new Site();
        model.addAttribute("idTopologie", id);
        model.addAttribute("siteRequis", siteRequis);
        logger.info(siteTrouve);
        model.addAttribute("siteTrouve", siteTrouve);
        return "addSiteTopo";
        //
    }

    /**
     * Méthode permet de rattacher le topo sur le site en post
     *
     * @param model
     * @param siteSelectionne
     * @param id
     * * @return la page "addSiteTopo"
     */
    @RequestMapping(value = "addSiteTopo/{id}", method = RequestMethod.POST)
    public String saveSiteTopo(@PathVariable("id") Long id, Site siteSelectionne, Model model) {
        logger.info(id);
        logger.info(siteSelectionne);
        Topologie topo = this.topologieService.getTopologieById(id);
        siteSelectionne = siteRepository.getOne(siteSelectionne.getId());
        siteSelectionne.setTopologie(topo);
        this.siteService.saveSite(siteSelectionne);
        model.addAttribute("topologies", this.topologieService.getAllTopologies());
        return "redirect:/topologie/listTopologieByUser";
    }

    /**
     * Méthode permet de modifier le topo en get
     *
     * @param model
     * @param id
     * * @return la page "editionTopologie"
     */
    @RequestMapping(value = "/editionTopologie/{id}", method = RequestMethod.GET)
    public String editionTopologie(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("topologie", this.topologieService.getTopologieById(id));
        return "editionTopologie";

    }

    /**
     * Méthode permet de modifier le topo en post
     * @param model
     * @param id
     * @param errors
     * * @return la page "listTopologie"
     */
    @RequestMapping(value = "/editionTopologie/{id}", method = RequestMethod.POST)
    public String editionTopologie(@PathVariable(value = "id") long id,Topologie Topologie, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editionTopologie";
        } else {
            Topologie topologieId = this.topologieService.getTopologieById(id);
            topologieId.setIsofficiel(Topologie.getIsofficiel());
            topologieId.setNomTopologie(Topologie.getNomTopologie());
            topologieId.setSecteur(Topologie.getSecteur());
            topologieId.setUtilisateur(this.utilisateurService.getUtilisateurConnected());
            this.topologieService.saveTopologie(topologieId);
            logger.info(Topologie);
            model.addAttribute("topologies", this.topologieService.findTopologieByUser(this.utilisateurService.getUtilisateurConnected()));
            return "redirect:/topologie/listTopologieByUser";
        }
    }

    /**
     * Méthode permet de vérifier l'existence du topo
     * @param model
     * @param id
     * * @return la page "editionTopologie"
     */
    @RequestMapping(value = "/editionTopologie1", method = RequestMethod.GET)
    public String editionTopologie2(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("topologie", this.topologieService.getTopologieById(id));
        return "editionTopologie";

    }

    /**
     * Méthode permet d'effacer le topo
     * @param model
     * @param id
     * @param topologie
     * @param errors
     * * @return la page "listTopologie"
     */
    @RequestMapping(value = "/deleteTopologie1", method = RequestMethod.POST)
    public String deleteTopologie(@RequestParam(value = "id") long id, @Valid @ModelAttribute Topologie topologie, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editionTopologie";
        } else {
            this.topologieService.deleteTopologiesById(topologie.getId());
            return "listTopologie";
        }
    }

    /**
     * Méthode permet de lister les topologies de l'utilisateur connecte
     * @param model
     * * @return la page "listTopologieByUser("
     */
    @RequestMapping(value = "/listTopologieByUser", method = RequestMethod.GET)
    public String TopoListUser(Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        logger.info(utilisateurId);
        model.addAttribute("topologiesbyuser", this.topologieService.findTopologieByUser(utilisateurId));
        return "listTopologieByUser";
    }


    /**
     * Méthode permet de lister les topologies publiques
     * @param model
     * * @return la page "listTopologiePublic"
     */
    @RequestMapping(value = "/listTopologiePublic", method = RequestMethod.GET)
    public String TopoListPublic(Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
    //    model.addAttribute("topologiepublic", this.topologieService.findTopologieByIspublic());
        model.addAttribute("topologiepublic", this.topologieService.findTopologieByPublicAndIspublic(utilisateurId));
        return "listTopologiePublic";
    }

    /**
     * Méthode permet de partager le topo avec les autres utilisateurs en get
     * @param model
     * * @return la page "listTopologiePublic"
     */
    @RequestMapping(value = "/makeTopoPublic/{id}", method = RequestMethod.GET)
    public String makeTopoPublic(@PathVariable(value = "id") Long id, Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        Topologie topologieId = this.topologieService.getTopologieById(id);
        model.addAttribute("idTopologie", id);
        model.addAttribute("topologie", topologieId); // topologiebyid en commentaire
        return "publicationTopologie";
    }

    /**
     * Méthode permet de partager le topo avec les autres utilisateurs en post
     * @param model
     * * @return la page "listTopologiePublic"
     */
    @RequestMapping(value = "/makeTopoPublic/{id}", method = RequestMethod.POST)
    public String saveTopoPublic(@PathVariable(value = "id") Long id, Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        Topologie topologieId = this.topologieService.getTopologieById(id);
        if (topologieId.getIspublic() == Boolean.FALSE) {
            topologieId.setIspublic(Boolean.TRUE);
            topologieId.setIsavailable(Boolean.TRUE);
            this.topologieService.saveTopologie(topologieId);
            model.addAttribute("topologiepublic", this.topologieService.findTopologieByPublicAndIspublic(utilisateurId));
        }
        return "redirect:/admin/home";
    }

    /**
     * Méthode permet de chercher le topo en fonction des critères en get
     * @param model
     * * @return la page "topoSearch"
     */
    @RequestMapping(value = "/SearchTopoList", method = RequestMethod.GET)
    public String listTopoSearch(Model model) {
        model.addAttribute("topologie", new Topologie());
        logger.info(new Topologie());
        return "topoSearch";
    }

    /**
     * Méthode permet de chercher le topo en fonction des critères en post
     * @param model
     * * @return la page "SearchforTopo"
     */
    @RequestMapping(value = "/SearchTopoList", method = RequestMethod.POST)
    public String saveTopoSearchList(Model model, Topologie topologieEnrecherche, String recherche) {
        String nomTopologie = topologieEnrecherche.getNomTopologie();
        String secteur = topologieEnrecherche.getSecteur();
        String pays = topologieEnrecherche.getPays();
        logger.info(nomTopologie);
        logger.info(secteur);
        recherche = (nomTopologie);
        List<Topologie> topologieRecherche = this.topologieService.findTopologieBySecteurOrNom(recherche);
        logger.info(this.topologieService.findTopologieBySecteurOrNom(recherche));
        model.addAttribute("topologiesearch", this.topologieService.findTopologieBySecteurOrNom(recherche));
        return "searchListTopo";
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
     * *  @return la page "listReservationByUser"
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
        model.addAttribute("reservationsbyuserdifferent", this.reservationService.findReservationByUser(utilisateurId));
        return "redirect:/reservation/listReservationByUser";
    }

    /**
     * Méthode permet de chercher le site en fonction des critères en get
     * @param model
     * * @return la page "siteSearch"
     */
    @RequestMapping(value = "/SearchSiteList", method = RequestMethod.GET)
    public String listTopoSearch1(Model model) {
        model.addAttribute("site", new Site());
        logger.info(new Site());
        return "siteSearch";
    }

    /**
     * Méthode permet de chercher le site en fonction des critères en post
     * @param model
     * * @return la page "SearchforSite"
     */
    @RequestMapping(value = "/SearchSiteList", method = RequestMethod.POST)
    public String saveTopoSearchList1(Model model, Site SiteEnrecherche, String recherche) {
        String nomSite = SiteEnrecherche.getNomSite();
        logger.info(nomSite);
        recherche = (nomSite);
        List<Site> SiteRecherche = this.siteService.findSiteByNom(recherche);
        logger.info(this.topologieService.findTopologieBySecteurOrNom(recherche));
        model.addAttribute("sitesearch", this.siteService.findSiteByNom(recherche));
        return "searchListSite";
    }

    @RequestMapping(value = "/SearchVoieList", method = RequestMethod.GET)
    public String listVoieSearch1(Model model) {
        model.addAttribute("site", new Voie());
        logger.info(new Voie());
        return "voieSearch";
    }

    /**
     * Méthode permet de chercher la voie en fonction des critères en post
     * @param model
     * * @return la page "SearchforVoie"
     */
    @RequestMapping(value = "/SearchVoieList", method = RequestMethod.POST)
    public String saveVoieSearchList(Model model, Voie VoieEnrecherche, String recherche) {
        String nomVoie = VoieEnrecherche.getNomVoie();
        String cotation = VoieEnrecherche.getCotation();
        recherche = (nomVoie);
        List<Voie> VoieRecherche = this.voieService.findVoieByNomOrCotation(recherche);
        logger.info(this.voieService.findVoieByNomOrCotation(recherche));
        model.addAttribute("voiesearch", this.voieService.findVoieByNomOrCotation(recherche));
        return "searchListVoie";
    }

}

