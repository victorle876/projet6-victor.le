package com.escalade.victor.controller;

import com.escalade.victor.model.*;
import com.escalade.victor.repository.SecteurRepository;
import com.escalade.victor.repository.SiteRepository;
import com.escalade.victor.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/topo")
@Controller
public class TopoController {

    @Autowired
    private TopoService topoService;

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

    private static final Logger logger = LogManager.getLogger(TopoController.class);

    /**
     * Méthode permet de lister toutes les topos
     *
     * @param model
     * * @return la page "listTopo"
     */
    @RequestMapping(value = "/listTopo", method = RequestMethod.GET)
    public String TopoList(Model model) {
        model.addAttribute("topologies", this.topoService.getAllTopos());
        return "listTopologie";
    }

    /**
     * Méthode permet de voir en detail la topologie
     *
     * @param model
     * @param id
     * * @return la page "detailsTopo"
     */
    @RequestMapping(value = "/detailsTopo", method = RequestMethod.GET)
    public String detail(@RequestParam(value = "id") Long id, Model model) {
        Topo topologie = topoService.getTopoById(id);
        if (topologie == null) {
            logger.debug("le topologie n'existe pas");
        }
        logger.info(this.topoService.getTopoById(id));
        model.addAttribute("topo", this.topoService.getTopoById(id));
        return "detailsTopologie";

    }

    /**
     * Méthode permet d'ajouter le topo en get
     *
     * @param model
     * * @return la page "addTopologie"
     */
    @RequestMapping(value = "/addTopo", method = RequestMethod.GET)
    public String ajouterTopologie(Model model) {
        model.addAttribute("topo", new Topo());
        return "addTopologie";
    }

    /**
     * Méthode permet d'ajouter le topologie en post
     *
     * @param model
     * @param topo
     * @param result
     * * @return la page "listTopologie"
     */
    @RequestMapping(value = "/saveTopo", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute Topo topo, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "addTopologie";
        } else {
            this.utilisateurService.getUtilisateurConnected();
            Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
            topo.setUtilisateur(utilisateurId);
            topo.setIspublic(Boolean.FALSE);
            logger.info("utilisateurId");
            this.topoService.saveTopo(topo);
            model.addAttribute("topologies", this.topoService.findTopoByUser(utilisateurId));
            return "redirect:/topo/listTopoByUser";
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
        List<Site> siteTrouve = this.topoService.findSiteByUser(utilisateurId);
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
        Topo topo = this.topoService.getTopoById(id);
        siteSelectionne = siteRepository.getOne(siteSelectionne.getId());
        siteSelectionne.setTopo(topo);
        this.siteService.saveSite(siteSelectionne);
        model.addAttribute("topologies", this.topoService.getAllTopos());
        return "redirect:/topo/listTopoByUser";
    }

    /**
     * Méthode permet de modifier le topo en get
     *
     * @param model
     * @param id
     * * @return la page "editionTopologie"
     */
    @RequestMapping(value = "/editionTopo/{id}", method = RequestMethod.GET)
    public String editionTopologie(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("topo", this.topoService.getTopoById(id));
        return "editionTopologie";

    }

    /**
     * Méthode permet de modifier le topo en post
     * @param model
     * @param id
     * @param errors
     * * @return la page "listTopologie"
     */
    @RequestMapping(value = "/editionTopo/{id}", method = RequestMethod.POST)
    public String editionTopologie(@PathVariable(value = "id") long id, Topo Topologie, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editionTopologie";
        } else {
            Topo topologieId = this.topoService.getTopoById(id);
            topologieId.setNomTopologie(Topologie.getNomTopologie());
            topologieId.setDescription(Topologie.getDescription());
            topologieId.setUtilisateur(this.utilisateurService.getUtilisateurConnected());
            this.topoService.saveTopo(topologieId);
            logger.info(Topologie);
            model.addAttribute("topologies", this.topoService.findTopoByUser(this.utilisateurService.getUtilisateurConnected()));
            return "redirect:/topo/listTopoByUser";
        }
    }

    /**
     * Méthode permet de vérifier l'existence du topo
     * @param model
     * @param id
     * * @return la page "editionTopologie"
     */
    @RequestMapping(value = "/editionTopo1", method = RequestMethod.GET)
    public String editionTopologie2(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("topologie", this.topoService.getTopoById(id));
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
    @RequestMapping(value = "/deleteTopo1", method = RequestMethod.POST)
    public String deleteTopologie(@RequestParam(value = "id") long id, @Valid @ModelAttribute Topo topologie, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editionTopologie";
        } else {
            this.topoService.deleteToposById(topologie.getId());
            return "listTopologie";
        }
    }

    /**
     * Méthode permet de lister les topologies de l'utilisateur connecte
     * @param model
     * * @return la page "listTopologieByUser"
     */
    @RequestMapping(value = "/listTopoByUser", method = RequestMethod.GET)
    public String TopoListUser(Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        logger.info(utilisateurId);
        model.addAttribute("topologiesbyuser", this.topoService.findTopoByUser(utilisateurId));
        return "listTopologieByUser";
    }


    /**
     * Méthode permet de lister les topologies publiques
     * @param model
     * * @return la page "listTopologiePublic"
     */
    @RequestMapping(value = "/listTopoPublic", method = RequestMethod.GET)
    public String TopoListPublic(Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        model.addAttribute("topologiepublic", this.topoService.findTopoByPublicAndIspublic(utilisateurId));
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
        Topo topologieId = this.topoService.getTopoById(id);
        model.addAttribute("id", id);
        model.addAttribute("topo", topologieId);
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
        Topo topologieId = this.topoService.getTopoById(id);
        if (topologieId.getIspublic() == Boolean.FALSE) {
            topologieId.setIspublic(Boolean.TRUE);
            topologieId.setIsavailable(Boolean.TRUE);
            this.topoService.saveTopo(topologieId);
            model.addAttribute("topologiesbyuser", this.topoService.getAllTopos());
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
        model.addAttribute("topologie", new Topo());
        logger.info(new Topo());
        return "topoSearch";
    }

    /**
     * Méthode permet de chercher le topo en fonction des critères en post
     * @param model
     * * @return la page "SearchforTopo"
     */
    @RequestMapping(value = "/SearchTopoList", method = RequestMethod.POST)
    public String saveTopoSearchList(Model model, Topo topologieEnrecherche, String recherche) {
        String nomTopologie = topologieEnrecherche.getNomTopologie();
        logger.info(nomTopologie);
        recherche = (nomTopologie);
        List<Topo> topologieRecherche = this.topoService.findTopoBySecteurOrNom(recherche);
        logger.info(this.topoService.findTopoBySecteurOrNom(recherche));
        model.addAttribute("topologiesearch", this.topoService.findTopoBySecteurOrNom(recherche));
        return "searchListTopo";
    }

    /**
     * Méthode permet d'ajouter une reservation sur un topo en get
     *
     * @param id
     * @param model
     * *  @return la page "listTopologiePublic"
     */
    @RequestMapping(value = "addTopoReservation/{id}", method = RequestMethod.GET)
    public String addTopoReservation(@PathVariable("id") Long id, Model model) {
        Topo topologieId = this.topoService.getTopoById(id);
        model.addAttribute("id", id);
        model.addAttribute("topo", topologieId);
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
    public String saveTopoReservation(@PathVariable("id") Long id, Model model, Topo topologie, Reservation newReservation) {
        Topo topologieId = this.topoService.getTopoById(id);
        logger.info(topologieId);
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        newReservation.setTopo(topologieId);
        newReservation.setEtat("En cours");
        newReservation.setUtilisateur(utilisateurId);
        this.reservationService.saveReservation(newReservation);
        topologieId.setIsavailable(Boolean.FALSE);
        this.topoService.saveTopo(topologieId);
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
    public String saveTopoSearchList1(Model model, Site SiteEnrecherche) {
        String nomSite = SiteEnrecherche.getNomSite();
        String pays = SiteEnrecherche.getPays();
        String nombreSecteur = SiteEnrecherche.getNombreSecteur();
        logger.info(nomSite);
        String recherche = nomSite;
        String recherche1 = nombreSecteur;
        String recherche2 = pays;
        List<Site> SiteRechercheByNom = this.siteService.findSiteByNom(recherche);
        logger.info(SiteRechercheByNom);
        List<Site> SiteRechercheByNombreSecteur = this.siteService.findSiteByNombreSecteur(recherche1);
        List<Site> SiteRechercheByPays = this.siteService.findSiteByPays(recherche1);
        List<Site> SiteRechercheByNombreSecteurAndPays = this.siteService.findSiteByNombreSecteurAndPays(recherche1,recherche2);
        List<Site> SiteRechercheByAll = this.siteService.findSiteByAll(recherche,recherche1,recherche2);
        if (recherche != null)
        {
            model.addAttribute("sitesearch", this.siteService.findSiteByNom(recherche));
        }
        if (recherche1 != null)
        {
            model.addAttribute("sitesearch", this.siteService.findSiteByNombreSecteur(recherche1));
        }
        if (recherche2 != null)
        {
            model.addAttribute("sitesearch", this.siteService.findSiteByPays(recherche2));
        }

        if ((recherche2 != null) && (recherche1 != null))
        {
            model.addAttribute("sitesearch", this.siteService.findSiteByNombreSecteurAndPays(recherche1,recherche2));
        }

   //     model.addAttribute("sitesearch", this.siteService.findSiteByAll(recherche,recherche1,recherche2));
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

