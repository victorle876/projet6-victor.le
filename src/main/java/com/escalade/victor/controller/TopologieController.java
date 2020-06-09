package com.escalade.victor.controller;

import com.escalade.victor.model.*;
import com.escalade.victor.repository.SiteRepository;
import com.escalade.victor.service.SiteService;
import com.escalade.victor.service.TopologieService;
import com.escalade.victor.service.UtilisateurService;
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
    private SiteService siteService;

    @Autowired
    private SiteRepository siteRepository;

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
            this.utilisateurService.getUtilisateurConnected();
            Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
            topologie.setUtilisateur(utilisateurId);
            System.out.println(utilisateurId);
            topologie.setIspublic(Boolean.FALSE); // mise à flag false pour topo non publié
            this.topologieService.saveTopologie(topologie);
            model.addAttribute("topologies", this.topologieService.getAllTopologies());
            return "listTopologie";
        }
    }

        @RequestMapping(value = "addSiteTopo/{id}", method = RequestMethod.GET)
        public String addSiteTopo(@PathVariable("id") Long id, Model model, Site site) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        List<Site> siteTrouve = this.topologieService.findSiteByUser(utilisateurId);
        Site siteRequis = new Site();
        model.addAttribute("idTopologie", id);
        model.addAttribute("siteRequis", siteRequis);
        System.out.println(siteTrouve);
        model.addAttribute("siteTrouve",siteTrouve);
        return "addSiteTopo";
        //
    }

    @RequestMapping(value = "addSiteTopo/{id}", method = RequestMethod.POST)
    public String saveSiteTopo(@PathVariable("id") Long id, Site siteSelectionne, Model model) {
        System.out.println(id);
        System.out.println(siteSelectionne);
        Topologie topo = this.topologieService.getTopologieById(id);
        siteSelectionne = siteRepository.getOne(siteSelectionne.getId());
        siteSelectionne.setTopologie(topo);
        this.siteService.saveSite(siteSelectionne);
        model.addAttribute("topologies", this.topologieService.getAllTopologies());
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
            Topologie = this.topologieService.getTopologieById(id);
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
            return "listTopologie";
        }
    }

    @RequestMapping(value = "/listTopologieByUser", method = RequestMethod.GET)
    public String TopoListUser(Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        System.out.println(utilisateurId);
        model.addAttribute("topologiesbyuser", this.topologieService.findTopologieByUser(utilisateurId));
        return "listTopologieByUser";
    }

    @RequestMapping(value = "/listTopologieDifferentByUser", method = RequestMethod.GET)
    public String TopoListDifferentUser(Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        System.out.println(utilisateurId);
        model.addAttribute("topologiesbyuserdifferent", this.topologieService.findTopologieByUserDifferent(utilisateurId));
        return "listTopologieDifferent";
    }

    @RequestMapping(value = "/listTopologiePublic", method = RequestMethod.GET)
    public String TopoListPublic(Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        model.addAttribute("topologiepublic", this.topologieService.findTopologieByPublicAndIspublic(utilisateurId));
        return "listTopologiePublic";
    }

    @RequestMapping(value = "/makeTopoPublic/{id}", method = RequestMethod.GET)
    public String makeTopoPublic(@PathVariable(value = "id") Long id, Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        Topologie topologieId = this.topologieService.getTopologieById(id);
        model.addAttribute("idTopologie", id);
        model.addAttribute("topologie", topologieId); // topologiebyid en commentaire
        return "publicationTopologie";
    }

    @RequestMapping(value = "/makeTopoPublic/{id}", method = RequestMethod.POST)
    public String saveTopoPublic(@PathVariable(value = "id") Long id, Model model) {
        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        Topologie topologieId = this.topologieService.getTopologieById(id);
        if (topologieId.getIspublic() == Boolean.FALSE){
            topologieId.setIspublic(Boolean.TRUE);
    //        topologieId.setUtilisateur(null);
            this.topologieService.saveTopologie(topologieId);
            model.addAttribute("topologiepublic", this.topologieService.findTopologieByPublicAndIspublic(utilisateurId));
        }
        return "listTopologiePublic";
    }

}

