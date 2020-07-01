package com.escalade.victor.controller;

import com.escalade.victor.model.*;
import com.escalade.victor.repository.*;
import com.escalade.victor.service.*;
import com.escalade.victor.service.UtilisateurService;
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
import java.util.List;

@RequestMapping("/site")
@Controller
public class SiteController {
    @Autowired
    private SiteService siteService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private TopologieRepository topologieRepository;

    @Autowired
    private TopologieService topologieService;

    @Autowired
    private CommentaireService commentaireService;

    @Autowired
    private VoieService voieService;

    private static final Logger logger = LogManager.getLogger(SiteController.class);

    /**
     * Méthode permet de lister les sites
     *
     * @param model
     * * @return la page "listSite"
     */
    @RequestMapping(value = "/listSite", method = RequestMethod.GET)
    public String SiteList(Model model) {
        model.addAttribute("sites", this.siteService.getAllSites());
        return "listSite";
    }

    /**
     * Méthode permet de voir le site en detail
     *
     * @param model
     * @param id
     * * @return la page "detailsSite"
     */
    @RequestMapping(value = "/detailsSite", method = RequestMethod.GET)
    public String detail(@RequestParam(value = "id") Long id, Model model) {
        Site site = siteService.getSiteById(id);
        if (site == null) {
            logger.info("le site n'existe pas");
        }
        logger.info(this.siteService.getSiteById(id));
        List<Commentaire> commentaireList = this.commentaireService.findCommentaireBySite(site);
        logger.info(commentaireList);
        model.addAttribute("site", this.siteService.getSiteById(id));
        model.addAttribute("commentairesbysite", commentaireList);
        return "detailsSite";

    }

    /**
     * Méthode permet d'ajouter le site sur le topo en get
     *
     * @param model
     * * @return la page "addSite"
     */
    @RequestMapping(value = "/addSite", method = RequestMethod.GET)
    public String ajouterSite(Model model) {
        model.addAttribute("site", new Site());
        return "addSite";
    }


    /**
     * Méthode permet d'ajouter le site sur le topo en post
     *
     * @param model
     * @param site
     * @param result
     * @param utilisateur
     * * @return la page "listSite"
     */
    @RequestMapping(value = "/saveSite", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute Site site, Utilisateur utilisateur, Model model, BindingResult result) {

        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        logger.info(utilisateurId);
        if (result.hasErrors()) {
            return "addSite";
        } else {
          site.setUtilisateur(utilisateurId);
            this.siteService.saveSite(site);
            model.addAttribute("sites", this.siteService.getAllSites());
             return "listSite";
        }
    }

    /**
     * Méthode permet de rattacher le site sur le topo en get
     *
     * @param model
     * @param idSite
     * @param topologie
     * * @return la page "listSite"
     */
    @RequestMapping(value = "addTopoSite/{id}", method = RequestMethod.GET)
    public String addTopoSite(@PathVariable("id") Long idSite, Model model, Topologie topologie) {
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        List<Topologie> topologieTrouve = this.topologieService.findTopologieByUser(utilisateurId);
        logger.info(topologieTrouve);
        Site site = this.siteService.getSiteById(idSite);
        Topologie topologieRequis = new Topologie();
        if (site.getTopologie() != null){
            topologieRequis = site.getTopologie() ;
        }
        model.addAttribute("idSite", idSite);
        model.addAttribute("topologieRequis",topologieRequis);
        model.addAttribute("topologieTrouve",topologieTrouve);
        return "addTopoSite";
        //
    }

    /**
     * Méthode permet de rattacher le site sur le topo en post
     *
     * @param model
     * @param idSite
     * @param topologieSelectionne
     * * @return la page "addTopoSite"
     */
    @RequestMapping(value = "addTopoSite/{id}", method = RequestMethod.POST)
    public String saveTopoSite(@PathVariable("id") Long idSite, Model model, Topologie topologieSelectionne) {
        topologieSelectionne = topologieRepository.getOne(topologieSelectionne.getId());
        logger.info(topologieSelectionne);
        logger.info(idSite);
        Site siteSelectionne = this.siteService.getSiteById(idSite);
        siteSelectionne.setTopologie(topologieSelectionne);
        logger.info(siteSelectionne);
        this.siteService.saveSite(siteSelectionne);
        model.addAttribute("sites", this.siteService.getAllSites());
        return "addTopoSite";
    }

    /**
     * Méthode permet de modifier le site en get
     *
     * @param model
     * @param id
     * * @return la page "editionSite"
     */
    @RequestMapping(value = "/editionSite", method = RequestMethod.GET)
    public String editionSite(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("site", this.siteService.getSiteById(id));
        return "editionSite";

    }

    /**
     * Méthode permet de modifier le site en post
     *
     * @param model
     * @param id
     * * @return la page "editionSite"
     */
    @RequestMapping(value = "/editionSite", method = RequestMethod.POST)
    public String editionSite(@RequestParam(value = "id") long id, @Valid @ModelAttribute Site site, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editionSite";
        } else {
            site = this.siteService.getSiteById(id);
            this.siteService.saveSite(site);
            model.addAttribute("sites", this.siteService.getAllSites());
            return "redirect:/";
        }
    }

    /**
     * Méthode permet d'effacer le site en get
     *
     * @param model
     * @param id
     * * @return la page "deleteSite"
     */
    @RequestMapping(value = "/editionSite1", method = RequestMethod.GET)
    public String editionSite2(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("site", this.siteService.getSiteById(id));
        return "editionSite";

    }

    /**
     * Méthode permet d'effacer le site en get
     *
     * @param model
     * @param id
     * @param site
     * @param errors
     * * @return la page "deleteSite"
     */
    @RequestMapping(value = "/deleteSite1", method = RequestMethod.POST)
    public String deleteSite(@RequestParam(value = "id") long id, @Valid @ModelAttribute Site site, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editionSite";
        } else {
            this.siteService.deleteSiteById(site.getId());
            return "redirect:/";
        }
    }

    /**
     * Méthode permet d'ajouter la voie sur le site en get
     *
     * @param model
     * @param id
     * @param site
     * * @return la page "addVoie"
     */
    @RequestMapping(value = "/addVoieSite/{id}", method = RequestMethod.GET)
    public String ajouterVoieSite(@PathVariable("id") Long id, Model model, Site site) {
        Site siteId = this.siteService.getSiteById(id);
        model.addAttribute("voie", new Voie());
        model.addAttribute("siteId", siteId);
        return "addVoie";
    }

    /**
     * Méthode permet d'ajouter la voie sur le site en post
     *
     * @param model
     * @param id
     * @param site
     * * @return la page "home"
     */
    @RequestMapping(value = "/addVoieSite/{id}", method = RequestMethod.POST)
    public String saveVoieSite(@PathVariable("id") Long id, @Valid @ModelAttribute Voie voie, Site site, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "addVoie";
        } else {
            Site siteId = this.siteService.getSiteById(id);
            this.utilisateurService.getUtilisateurConnected();
            Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
            voie.setSite(siteId);
            voie.setUtilisateur(utilisateurId);
            this.voieService.saveVoie(voie);
            model.addAttribute("voies", this.voieService.getAllVoies());
            return "home";
        }
    }

    /**
     * Méthode permet d'ajouter le commentaire sur le site en get
     *
     * @param model
     * @param id
     * @param site
     * * @return la page "home"
     */
    @RequestMapping(value = "/addCommentaireSite/{id}", method = RequestMethod.GET)
    public String ajouterCommentaireSite(@PathVariable("id") Long id, Model model, Site site) {
        Site siteId = this.siteService.getSiteById(id);
        model.addAttribute("commentaire", new Commentaire());
        model.addAttribute("siteId", siteId);
        return "addCommentaire";
    }

    /**
     * Méthode permet d'ajouter le commentaire sur le site en post
     *
     * @param model
     * @param id
     * @param site
     * * @return la page "home"
     */
    @RequestMapping(value = "/addCommentaireSite/{id}", method = RequestMethod.POST)
    public String saveCommentaireSite(@PathVariable("id") Long id, @Valid @ModelAttribute Commentaire commentaire, Site site, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "addCommentaire";
        } else {
            Site siteId = this.siteService.getSiteById(id);
            this.utilisateurService.getUtilisateurConnected();
            Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
            commentaire.setSite(siteId);
            commentaire.setUtilisateur(utilisateurId);
            this.commentaireService.saveCommentaire(commentaire);
            model.addAttribute("commentaires", this.commentaireService.getAllCommentaires());
            return "home";
        }
    }

    /**
     * Méthode permet de lister tous les sites appartenant à l'utilisateur
     *
     * @param model
     * * @return la page "listSiteByUser"
     */
    @RequestMapping(value = "/listSiteByUser", method = RequestMethod.GET)
    public String SiteListByUser(Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        model.addAttribute("sitebyuser", this.siteService.findSiteByUser(utilisateurId));
        return "listSiteByUser";
    }

}
