package com.escalade.victor.controller;

import com.escalade.victor.model.Site;
import com.escalade.victor.model.Topologie;
import com.escalade.victor.model.Utilisateur;
import com.escalade.victor.model.Voie;
import com.escalade.victor.service.SiteService;
import com.escalade.victor.service.UtilisateurService;
import com.escalade.victor.service.VoieService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/voie")
@Controller
public class VoieController {
    @Autowired
    private VoieService voieService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private SiteService siteService;

    private static final Logger logger = LogManager.getLogger(VoieController.class);

    /**
     * Méthode permet de lister tous les voies
     *
     * @param model
     * * @return la page "listVoie"
     */
    @RequestMapping(value = "/listVoie", method = RequestMethod.GET)
    public String VoieList(Model model) {
        model.addAttribute("voies", this.voieService.getAllVoies());
        return "listVoie";
    }


    /**
     * Méthode permet de voir en detail la voie
     *
     * @param model
     * @param id
     * * @return la page "detailsVoie"
     */
    @RequestMapping(value = "/detailsVoie", method = RequestMethod.GET)
    public String detail(@RequestParam(value = "id") Long id, Model model) {
        Voie Voie = voieService.getVoieById(id);
        if (Voie == null) {
            logger.info("la voie n'existe pas");
        }
        model.addAttribute("voie", this.voieService.getVoieById(id));
        return "detailsVoie";

    }

    /**
     * Méthode permet de modifier la voie en get
     *
     * @param model
     * @param id
     * * @return la page "editionVoie"
     */
    @RequestMapping(value = "/editionVoie", method = RequestMethod.GET)
    public String editionTopologie(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("voie", this.voieService.getVoieById(id));
        return "editionVoie";

    }

    /**
     * Méthode permet de modifier la voie en post
     *
     * @param model
     * @param id
     * * @return la page "editionVoie"
     */
    @RequestMapping(value = "/editionVoie", method = RequestMethod.POST)
    public String editionVoie(@RequestParam(value = "id") long id, @Valid @ModelAttribute Voie Voie, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editionVoie";
        } else {
            this.voieService.saveVoie(Voie);
            logger.info(this.voieService.getAllVoies());
            model.addAttribute("voies", this.voieService.getAllVoies());
            return "redirect:/";
        }
    }

    /**
     * Méthode permet de lister les voies de l'utilisateur
     *
     * @param model
     * * @return la page "listVoieByUser"
     */
    @RequestMapping(value = "/listVoieByUser", method = RequestMethod.GET)
    public String VoieListByUser(Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        logger.info(this.voieService.findVoieByUser(utilisateurId));
        model.addAttribute("voiesbyuser", this.voieService.findVoieByUser(utilisateurId));
        return "listVoieByUser";
    }
}
