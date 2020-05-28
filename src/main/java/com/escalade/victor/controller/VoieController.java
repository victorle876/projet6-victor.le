package com.escalade.victor.controller;

import com.escalade.victor.model.Site;
import com.escalade.victor.model.Topologie;
import com.escalade.victor.model.Utilisateur;
import com.escalade.victor.model.Voie;
import com.escalade.victor.service.SiteService;
import com.escalade.victor.service.UtilisateurService;
import com.escalade.victor.service.VoieService;
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

    @RequestMapping(value = "/listVoie", method = RequestMethod.GET)
    public String VoieList(Model model) {
        model.addAttribute("voies", this.voieService.getAllVoies());
        return "listVoie";
    }

/*    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String voieHome(Model model) { ;
        return "voiehome";
    }*/

    @RequestMapping(value = "/detailsVoie", method = RequestMethod.GET)
    public String detail(@RequestParam(value = "id") Long id, Model model) {
        Voie Voie = voieService.getVoieById(id);
        if (Voie == null) {
            System.out.println("la voie n'existe pas");
        }
        model.addAttribute("voie", this.voieService.getVoieById(id));
        return "detailsVoie";

    }

/*    @RequestMapping(value = "/addVoie", method = RequestMethod.GET)
    public String ajouterVoie(Model model) {
        model.addAttribute("voie", new Voie());
        return "addVoie";
    }

    @RequestMapping(value = "/saveVoie", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute Voie voie, Model model, BindingResult result) {

        if (result.hasErrors()) {
            return "addVoie";
        } else {
            this.voieService.saveVoie(voie);
            model.addAttribute("voies", this.voieService.getAllVoies());
            return "home";
        }
    }*/

    @RequestMapping(value = "/editionVoie", method = RequestMethod.GET)
    public String editionTopologie(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("voie", this.voieService.getVoieById(id));
        return "editionVoie";

    }

    @RequestMapping(value = "/editionVoie", method = RequestMethod.POST)
    public String editionVoie(@RequestParam(value = "id") long id, @Valid @ModelAttribute Voie Voie, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editionVoie";
        } else {
            this.voieService.saveVoie(Voie);
            model.addAttribute("voies", this.voieService.getAllVoies());
            return "redirect:/";
        }
    }

/*    @RequestMapping(value = "/listVoieByUser/{id}", method = RequestMethod.GET)
    public String VoieListByUser(@PathVariable("id") Long id,Model model) {*/
        @RequestMapping(value = "/listVoieByUser", method = RequestMethod.GET)
        public String VoieListByUser(Model model) {
//        Site siteId = this.siteService.getSiteById(id);
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        model.addAttribute("voiesbyuser", this.voieService.findVoieByUser(utilisateurId));
        return "listVoieByUser";
    }
}
