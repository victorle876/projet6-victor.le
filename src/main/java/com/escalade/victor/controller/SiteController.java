package com.escalade.victor.controller;

import com.escalade.victor.model.*;
import com.escalade.victor.repository.TopoRepository;
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

@RequestMapping("/site")
@Controller
public class SiteController {
    @Autowired
    private SecteurService secteurService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private TopoRepository topoRepository;

    @Autowired
    private TopoService topoService;

    @Autowired
    private CommentaireService commentaireService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private VoieService voieService;

    private static final Logger logger = LogManager.getLogger(SiteController.class);

    /**
     * Méthode permet de lister les secteurs
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
        logger.info("le site n'existe pas");
        logger.info(this.siteService.getSiteById(id));
        List<Commentaire> commentaireList = this.commentaireService.findCommentaireBySite(site);
        List<Secteur> secteurList = this.secteurService.findSecteurBySite(site);
        logger.info(commentaireList);
        model.addAttribute("site", this.siteService.getSiteById(id));
        model.addAttribute("commentairesbysite", commentaireList);
        model.addAttribute("secteursbysite", secteurList);
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
     * * @return la page "listSecteur"
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
            model.addAttribute("sitess", this.siteService.findSiteByUser(utilisateurId));
             return "redirect:/admin/home";
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
    public String addTopoSite(@PathVariable("id") Long idSite, Model model, Topo topologie) {
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        List<Topo> topologieTrouve = this.topoService.findTopoByUser(utilisateurId);
        logger.info(topologieTrouve);
        Site site = this.siteService.getSiteById(idSite);
        Topo topologieRequis = new Topo();
        if (site.getTopo() != null){
            topologieRequis = site.getTopo() ;
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
    public String saveTopoSite(@PathVariable("id") Long idSite, Model model, Topo topologieSelectionne) {
        topologieSelectionne = topoRepository.getOne(topologieSelectionne.getId());
        logger.info(topologieSelectionne);
        logger.info(idSite);
        Site siteSelectionne = this.siteService.getSiteById(idSite);
        siteSelectionne.setTopo(topologieSelectionne);
        logger.info(siteSelectionne);
        this.siteService.saveSite(siteSelectionne);
        model.addAttribute("sites", this.siteService.getAllSites());
        return "addTopoSite";
    }

    /**
     * Méthode permet de modifier le secteur en get
     *
     * @param model
     * @param id
     * * @return la page "editionSite"
     */
    @RequestMapping(value = "/editionSite/{id}", method = RequestMethod.GET)
    public String editionSecteur(@PathVariable(value = "id") Long id, Model model) {
        Site site = this.siteService.getSiteById(id);
        List<Commentaire> commentaireList = this.commentaireService.findCommentaireBySite(site);
        List<Secteur> secteurList = this.secteurService.findSecteurBySite(site);
        model.addAttribute("site", this.siteService.getSiteById(id));
        model.addAttribute("commentairesbysite", commentaireList);
        model.addAttribute("secteursbysite", secteurList);
        logger.info(commentaireList);
        return "editionSite";

    }

    /**
     * Méthode permet de modifier le site en post
     *
     * @param model
     * @param id
     * * @return la page "editionSite"
     */
    @RequestMapping(value = "/editionSite/{id}", method = RequestMethod.POST)
    public String editionSite(@PathVariable(value = "id") long id, Site site,Model model) {
            Site siteId = this.siteService.getSiteById(id);
            siteId.setNomSite(site.getNomSite());
            siteId.setNombreSecteur(site.getNombreSecteur());
            siteId.setIsofficiel(site.isIsofficiel());
            siteId.setPays(site.getPays());
            this.siteService.saveSite(siteId);
            model.addAttribute("sites", this.siteService.getAllSites());
            return "redirect:/site/listSiteByUser";
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
    public String saveCommentaireSite(@PathVariable("id") Long id, @Valid Commentaire commentaire, Site site, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "addCommentaire";
        } else {
            Commentaire commentaireCree = new Commentaire();
            Site siteId = this.siteService.getSiteById(id);
            this.utilisateurService.getUtilisateurConnected();
            Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
            commentaireCree.setSite(siteId);
            commentaireCree.setZoneCommentaire(commentaire.getZoneCommentaire());
            commentaireCree.setUtilisateur(utilisateurId);
            this.commentaireService.saveCommentaire(commentaireCree);
            model.addAttribute("commentaires", this.commentaireService.getAllCommentaires());
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
    @RequestMapping(value = "/addSecteurSite/{id}", method = RequestMethod.GET)
    public String ajouterSecteureSite(@PathVariable("id") Long id, Model model, Site site) {
        Site siteId = this.siteService.getSiteById(id);
        model.addAttribute("secteur", new Secteur());
        model.addAttribute("siteId", siteId);
        return "addSecteur";
    }

    /**
     * Méthode permet d'ajouter le commentaire sur le site en post
     *
     * @param model
     * @param id
     * @param site
     * * @return la page "home"
     */
    @RequestMapping(value = "/addSecteurSite/{id}", method = RequestMethod.POST)
    public String saveActeurSite(@PathVariable("id") Long id, @Valid Secteur secteur, Site site, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "addCommentaire";
        } else {
            Secteur secteurCree = new Secteur();
            Site siteId = this.siteService.getSiteById(id);
            this.utilisateurService.getUtilisateurConnected();
            Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
            secteurCree.setSite(siteId);
            secteurCree.setNomSecteur(secteur.getNomSecteur());
            secteurCree.setUtilisateur(utilisateurId);
            this.secteurService.saveSecteur(secteurCree);
            model.addAttribute("secteurs", this.secteurService.getAllSecteurs());
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
