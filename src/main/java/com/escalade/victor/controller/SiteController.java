package com.escalade.victor.controller;

import com.escalade.victor.model.*;
import com.escalade.victor.repository.*;
import com.escalade.victor.service.*;
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
    private VoieService voieService;

    @RequestMapping(value = "/listSite", method = RequestMethod.GET)
    public String SiteList(Model model) {
        model.addAttribute("sites", this.siteService.getAllSites());
        return "listSite";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String siteHome(Model model) {
        return "sitehome";
    }

    @RequestMapping(value = "/detailsSite", method = RequestMethod.GET)
    public String detail(@RequestParam(value = "id") Long id, Model model) {
        Site site = siteService.getSiteById(id);
        if (site == null) {
            System.out.println("le site n'existe pas");
        }
        model.addAttribute("site", this.siteService.getSiteById(id));
        return "detailsSite";

    }

    @RequestMapping(value = "/addSite", method = RequestMethod.GET)
    public String ajouterSite(Model model) {
        model.addAttribute("site", new Site());
        return "addSite";
    }

    @RequestMapping(value = "/saveSite", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute Site site, Utilisateur utilisateur, Model model, BindingResult result) {

        this.utilisateurService.getUtilisateurConnected();
        Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
        System.out.println(utilisateurId);
        if (result.hasErrors()) {
            return "addSite";
        } else {
          site.setUtilisateur(utilisateurId);
            this.siteService.saveSite(site);
            model.addAttribute("sites", this.siteService.getAllSites());
             return "listSite";
        }
    }

    @RequestMapping(value = "addTopoSite/{id}", method = RequestMethod.GET)
    public String addSiteTopo(@PathVariable("id") Long id, Model model, Site site) {
        model.addAttribute("idSite", id);
        model.addAttribute("topoTrouve", this.siteService.getSiteById(id));
        return "addTopoSite";
        //
    }

    @RequestMapping(value = "addTopoSite/{id}", method = RequestMethod.POST)
    public String saveSiteTopo(@PathVariable("id") Long id, Model model, Site site, Topologie topologie) {
        List <Site> SitesTopo = new ArrayList<Site>();
        Site siteForTopo = this.siteService.getSiteById(id);
        Topologie topologieCorrespondant = this.topologieRepository.findBySitesIsNull().get();
        Long idTopo = topologieCorrespondant.getId();
        if (siteForTopo.getTopologie().equals(idTopo)){
            this.topologieService.getTopologieById(idTopo);
            SitesTopo.add(siteForTopo);
            topologieCorrespondant.setSites(SitesTopo);
            this.topologieService.saveTopologie(topologieCorrespondant);
        }
        return "addTopoSite";
    }

    @RequestMapping(value = "/editionSite", method = RequestMethod.GET)
    public String editionSite(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("site", this.siteService.getSiteById(id));
        return "editionSite";

    }

    @RequestMapping(value = "/editionSite", method = RequestMethod.POST)
    public String editionSite(@RequestParam(value = "id") long id, @Valid @ModelAttribute Site site, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editionSite";
        } else {
            this.siteService.saveSite(site);
            model.addAttribute("sites", this.siteService.getAllSites());
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/editionSite1", method = RequestMethod.GET)
    public String editionSite2(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("site", this.siteService.getSiteById(id));
        return "editionSite";

    }

    @RequestMapping(value = "/deleteSite1", method = RequestMethod.POST)
    public String deleteSite(@RequestParam(value = "id") long id, @Valid @ModelAttribute Site site, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editionSite";
        } else {
            this.siteService.deleteSiteById(site.getId());
  //          model.addAttribute("sites", this.siteService.deleteSiteById(id));
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/addVoieSite/{id}", method = RequestMethod.GET)
    public String ajouterVoieSite(@PathVariable("id") Long id, Model model, Site site) {
        Site siteId = this.siteService.getSiteById(id);
        model.addAttribute("voie", new Voie());
        model.addAttribute("siteId", siteId);
        return "addVoie";
    }

    @RequestMapping(value = "/addVoieSite/{id}", method = RequestMethod.POST)
    public String saveVoieSite(@PathVariable("id") Long id, @Valid @ModelAttribute Voie voie, Site site, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "addVoie";
        } else {
            Site siteId = this.siteService.getSiteById(id);
            voie.setSite(siteId);
            this.voieService.saveVoie(voie);
            model.addAttribute("voies", this.voieService.getAllVoies());
            return "home";
        }
    }

}
