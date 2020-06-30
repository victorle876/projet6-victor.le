package com.escalade.victor.controller;

import com.escalade.victor.model.*;
import com.escalade.victor.config.*;
import com.escalade.victor.service.UtilisateurService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LogManager.getLogger(SiteController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("utilisateurs", this.utilisateurService.getAllUsers());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info(authentication);
        return "home";
    }

    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public String AdminHome(Model model) {
        return "adminhome";
    }


    @RequestMapping(value = "/listUser", method = RequestMethod.GET)
    public String UserList(Model model) {
        model.addAttribute("utilisateurs", this.utilisateurService.getAllUsers());
        return "listUser";

    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String ajouterUser(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "addUser";
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute Utilisateur utilisateur, Model model, BindingResult result) {

        if (result.hasErrors()) {
            return "addUser";
        } else {
            utilisateur.setPassword(this.passwordEncoder.encode(utilisateur.getPassword()));
            logger.info(utilisateur);
            this.utilisateurService.saveUser(utilisateur);
            model.addAttribute("utilisateurs", this.utilisateurService.getAllUsers());
            return "listUser";
        }
    }

    @RequestMapping(value = "/detailsUser", method = RequestMethod.GET)
    public String detail(@RequestParam(value = "id") Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.getUserById(id);
        if (utilisateur == null) {
            logger.info("l'utilisateur n'existe pas");
        }
        logger.info(this.utilisateurService.getUserById(id));
        model.addAttribute("utilisateur", this.utilisateurService.getUserById(id));
        return "detailsUser";
    }

    @RequestMapping(value = "/editionUser", method = RequestMethod.GET)
    public String editionUser(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("utilisateur", this.utilisateurService.getUserById(id));
        return "editionUser";

    }

    @RequestMapping(value = "/editionUser", method = RequestMethod.POST)
    public String editionUser(@RequestParam(value = "id") long id, @Valid @ModelAttribute Utilisateur utilisateur, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editionUser";
        } else {
            this.utilisateurService.saveUser(utilisateur);
            model.addAttribute("utilisateurs", this.utilisateurService.getAllUsers());
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/editionUser1", method = RequestMethod.GET)
    public String editionUser1(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("utilisateur", this.utilisateurService.getUserById(id));
        logger.info(this.utilisateurService.getUserById(id));
        return "editionUser";

    }

    @RequestMapping(value = "/deleteUser1", method = RequestMethod.POST)
    public String deleteUser(@RequestParam(value = "id") long id, @Valid @ModelAttribute Utilisateur utilisateur, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editionUser";
        } else {
            this.utilisateurService.deleteUserById(utilisateur.getId());
            //  model.addAttribute("utilisateurs", this.utilisateurService.deleteUserById(id));
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/connect", method = RequestMethod.GET)
    public String connect(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "connexion";

    }

}
