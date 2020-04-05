package com.escalade.victor.controller;

import com.escalade.victor.model.*;
import com.escalade.victor.service.TopologieService;
import com.escalade.victor.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping(value = "/listTopologie", method = RequestMethod.GET)
    public String TopoList(Model model) {
        model.addAttribute("topologies", this.topologieService.getAllTopologies());
        return "listTopologie";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String TopoHome(Model model) {
        return "topologiehome";
    }

    @RequestMapping(value = "/detailsTopologie", method = RequestMethod.GET)
    public String detail(@RequestParam(value = "id") Long id, Model model) {
        Topologie topologie = topologieService.getTopologieById(id);
        if (topologie == null) {
            System.out.println("le topologie n'existe pas");
        }
        model.addAttribute("topologie", this.topologieService.getTopologieById(id));
        return "detailsTopologie";

    }

    @RequestMapping(value = "/addTopologie", method = RequestMethod.GET)
    public String ajouterTopologie(Model model) {
        model.addAttribute("topologie", new Topologie());
        return "addTopologie";
    }

    @RequestMapping(value = "/saveTopologie", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute Topologie topologie, Model model, BindingResult result){
        if (result.hasErrors()) {
            return "addTopologie";
        } else {
            this.topologieService.saveTopologie(topologie);
            model.addAttribute("topologies", this.topologieService.getAllTopologies());
            return "listTopologie";
        }
    }

    @RequestMapping(value = "/addSiteTopo", method = RequestMethod.GET)
    public String ajouterSiteTopo(Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        List<Site> siteTrouve = this.topologieService.findSiteByUser(utilisateurId);
        Site siteRequis = new Site();
        model.addAttribute("siteRequis", siteRequis);
        System.out.println(siteTrouve);
        model.addAttribute("siteTrouve",siteTrouve);
        return "addSiteTopo";
    }

    @RequestMapping(value = "/editionTopologie", method = RequestMethod.GET)
    public String editionTopologie(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("topologie", this.topologieService.getTopologieById(id));
        return "editionTopologie";

    }

    @RequestMapping(value = "/editionTopologie", method = RequestMethod.POST)
    public String editionTopologie(@RequestParam(value = "id") long id, @Valid @ModelAttribute Topologie Topologie, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editionTopologie";
        } else {
            this.topologieService.saveTopologie(Topologie);
            model.addAttribute("topologies", this.topologieService.getAllTopologies());
            return "listTopologie";
        }
    }

    @RequestMapping(value = "/editionTopologie1", method = RequestMethod.GET)
    public String editionTopologie2(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("topologie", this.topologieService.getTopologieById(id));
        return "editionTopologie";

    }

    @RequestMapping(value = "/deleteTopologie1", method = RequestMethod.POST)
    public String deleteTopologie(@RequestParam(value = "id") long id, @Valid @ModelAttribute Topologie topologie, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editionTopologie";
        } else {
            this.topologieService.deleteTopologiesById(topologie.getId());
            //          model.addAttribute("Topologies", this.TopologieService.deleteTopologieById(id));
            return "listTopologie";
        }
    }
}

