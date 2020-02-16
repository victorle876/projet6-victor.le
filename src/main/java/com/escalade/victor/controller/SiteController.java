package com.escalade.victor.controller;

import com.escalade.victor.model.*;
import com.escalade.victor.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @RequestMapping(value = "/detailsSite", method = RequestMethod.GET)
    public String detail(@RequestParam(value = "id") Long id, Model model) {
        Site site = siteService.getSiteById(id);
        if (site == null) {
            System.out.println("le site n'existe pas");
        }
        model.addAttribute("site", this.siteService.getSiteById(id));
        return "detailSite";

    }

    @RequestMapping(value = "/addSite", method = RequestMethod.GET)
    public String ajouterSite(Model model) {
        model.addAttribute("site", new Site());
        return "addSite";
    }

    @RequestMapping(value = "/saveSite", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute Site site, Model model, BindingResult result) {

        if (result.hasErrors()) {
            return "addSite";
        } else {
            this.siteService.saveSite(site);
            model.addAttribute("sites", this.siteService.getAllSites());
            return "home";
        }
    }

    @RequestMapping(value = "/edition", method = RequestMethod.GET)
    public String editionSite(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("site", this.siteService.getSiteById(id));
        return "editSite";

    }

    @RequestMapping(value = "/editionSite", method = RequestMethod.POST)
    public String editionSite(@RequestParam(value = "id") long id, @Valid @ModelAttribute Site site, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editSite";
        } else {
            this.siteService.saveSite(site);
            model.addAttribute("sites", this.siteService.getAllSites());
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/editionSite1", method = RequestMethod.GET)
    public String editionSite2(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("site", this.siteService.getSiteById(id));
        return "editSite";

    }

    @RequestMapping(value = "/deleteSite1", method = RequestMethod.POST)
    public String deleteSite(@RequestParam(value = "id") long id, @Valid @ModelAttribute Site site, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editSite";
        } else {
            this.siteService.deleteSiteById(site.getId());
  //          model.addAttribute("sites", this.siteService.deleteSiteById(id));
            return "redirect:/";
        }
    }
}
