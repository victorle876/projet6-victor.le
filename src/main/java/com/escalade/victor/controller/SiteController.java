package com.escalade.victor.controller;

import com.escalade.victor.model.*;
import com.escalade.victor.service.SiteService;
import com.escalade.victor.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/site")
@Controller
public class SiteController {
    @Autowired
    private SiteService siteService;

    @Autowired
    private UtilisateurService utilisateurService;

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

        Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("test1");
        String usernameTrouve = authentication2.getName();
        System.out.println(usernameTrouve);
        Utilisateur utilisateurId = this.utilisateurService.findUserByid(usernameTrouve);
        System.out.println(utilisateurId);
        if (result.hasErrors()) {
            return "addSite";
        } else {
          site.setUtilisateur(utilisateurId);
            this.siteService.saveSite(site);
            model.addAttribute("sites", this.siteService.getAllSites());
    //        return "home";
             return "listSite";
        }
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
}
