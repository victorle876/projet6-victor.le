package com.escalade.victor.controller;

import com.escalade.victor.model.*;
import com.escalade.victor.repository.TopoRepository;
import com.escalade.victor.service.*;
import com.escalade.victor.service.UtilisateurService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/secteur")
@Controller
public class SecteurController {
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

    private static final Logger logger = LogManager.getLogger(SecteurController.class);

    /**
     * Méthode permet de lister les secteurs
     *
     * @param model
     * * @return la page "listSecteur"
     */
    @RequestMapping(value = "/listSecteur", method = RequestMethod.GET)
    public String SiteList(Model model) {
        model.addAttribute("secteurs", this.secteurService.getAllSecteurs());
        return "listSecteur";
    }

    /**
     * Méthode permet de voir le secteur en detail
     *
     * @param model
     * @param id
     * * @return la page "detailsSecteur"
     */
    @RequestMapping(value = "/detailsSecteur", method = RequestMethod.GET)
    public String detail(@RequestParam(value = "id") Long id, Model model) {
        Secteur secteur = this.secteurService.getSecteurById(id);
        logger.info("le secteur n'existe pas");
        logger.info(this.secteurService.getSecteurById(id));
        model.addAttribute("secteur", this.secteurService.getSecteurById(id));
        model.addAttribute("voiesbysecteur", this.voieService.findVoieBySecteur(secteur));
        return "detailsSecteur";

    }

    /**
     * Méthode permet d'ajouter le site sur le topo en get
     *
     * @param model
     * * @return la page "addSecteur"
     */
    @RequestMapping(value = "/addSecteur", method = RequestMethod.GET)
    public String ajouterSite(Model model) {
        model.addAttribute("secteur", new Secteur());
        return "addSecteur";
    }


    /**
     * Méthode permet d'ajouter le site sur le topo en post
     *
     * @param model
     * @param secteur
     * @param result
     * @param utilisateur
     * * @return la page "listSecteur"
     */
    @RequestMapping(value = "/saveSecteur", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute Secteur secteur, Utilisateur utilisateur, Model model, BindingResult result) {

        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        logger.info(utilisateurId);
        if (result.hasErrors()) {
            return "addSecteur";
        } else {
            secteur.setUtilisateur(utilisateurId);
            this.secteurService.saveSecteur(secteur);
            model.addAttribute("secteurs", this.secteurService.findSecteurByUser(utilisateurId));
             return "redirect:/admin/home";
        }
    }

    /**
     * Méthode permet de modifier le secteur en get
     *
     * @param model
     * @param id
     * * @return la page "editionSecteur"
     */
    @RequestMapping(value = "/editionSecteur/{id}", method = RequestMethod.GET)
    public String editionSecteur(@PathVariable(value = "id") Long id, Model model) {
        Secteur secteur = this.secteurService.getSecteurById(id);;
        model.addAttribute("secteur", this.secteurService.getSecteurById(id));
        model.addAttribute("voiesbysecteur", this.voieService.findVoieBySecteur(secteur));
        return "editionSecteur";

    }

    /**
     * Méthode permet de modifier le site en post
     *
     * @param model
     * @param id
     * * @return la page "editionSecteur"
     */
    @RequestMapping(value = "/editionSecteur/{id}", method = RequestMethod.POST)
    public String editionSite(@PathVariable(value = "id") long id, @Valid Secteur secteur, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editionSecteur";
        } else {
            Secteur secteurId = this.secteurService.getSecteurById(id);
            secteurId.setNomSecteur(secteur.getNomSecteur());
            this.secteurService.saveSecteur(secteurId);
            model.addAttribute("secteurs", this.secteurService.getAllSecteurs());
            return "redirect:/secteur/listSecteurByUser";
        }
    }

    /**
     * Méthode permet d'effacer le site en get
     *
     * @param model
     * @param id
     * * @return la page "deleteSite"
     */
    @RequestMapping(value = "/editionSecteur1", method = RequestMethod.GET)
    public String editionSite2(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("secteur", this.secteurService.getSecteurById(id));
        return "editionSecteur";

    }

    /**
     * Méthode permet d'effacer le secteur en get
     *
     * @param model
     * @param id
     * @param secteur
     * @param errors
     * * @return la page "deleteSecteur"
     */
    @RequestMapping(value = "/deleteSecteur1", method = RequestMethod.POST)
    public String deleteSite(@RequestParam(value = "id") long id, @Valid @ModelAttribute Secteur secteur, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editionSite";
        } else {
            this.secteurService.deleteSecteurById(secteur.getId());
            return "redirect:/";
        }
    }

    /**
     * Méthode permet d'ajouter la voie sur le site en get
     *
     * @param model
     * @param id
     * @param secteur
     * * @return la page "addVoie"
     */
    @RequestMapping(value = "/addVoieSecteur/{id}", method = RequestMethod.GET)
    public String ajouterVoieSite(@PathVariable("id") Long id, Model model, Secteur secteur) {
        Secteur secteurId = this.secteurService.getSecteurById(id);
        model.addAttribute("voie", new Voie());
        model.addAttribute("secteurId", secteurId);
        return "addVoie";
    }

    /**
     * Méthode permet d'ajouter la voie sur le site en post
     *
     * @param model
     * @param id
     * @param secteur
     * * @return la page "home"
     */
    @RequestMapping(value = "/addVoieSecteur/{id}", method = RequestMethod.POST)
    public String saveVoieSite(@PathVariable("id") Long id, @Valid Voie voie, Secteur secteur, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "addVoie";
        } else {
            Voie voieCree = new Voie();
            Secteur secteurId = this.secteurService.getSecteurById(id);
            this.utilisateurService.getUtilisateurConnected();
            Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
            voieCree.setSecteur(secteurId);
            voieCree.setCotation(voie.getCotation());
            voieCree.setNomVoie(voie.getNomVoie());
            voieCree.setHauteur(voie.getHauteur());
            voieCree.setDistance(voie.getDistance());
            voieCree.setUtilisateur(utilisateurId);
            this.voieService.saveVoie(voieCree);
            model.addAttribute("voiesbyuser", this.voieService.findVoieByUser(utilisateurId));
            return "redirect:/site/listSiteByUser";
        }
    }

    /**
     * Méthode permet de lister tous les sites appartenant à l'utilisateur
     *
     * @param model
     * * @return la page "listSecteurByUser"
     */
    @RequestMapping(value = "/listSecteurByUser", method = RequestMethod.GET)
    public String SecteurListByUser(Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        model.addAttribute("secteurbyuser", this.secteurService.findSecteurByUser(utilisateurId));
        return "listSecteurByUser";
    }

}
