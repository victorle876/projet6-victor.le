package com.escalade.victor.controller;

import com.escalade.victor.model.Secteur;
import com.escalade.victor.service.SecteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public class SecteurController {
    @Autowired
    private SecteurService secteurService;


    @RequestMapping(value = "/detailsSecteur", method = RequestMethod.GET)
    public String detail(@RequestParam(value = "id") Long id, Model model) {
        Secteur secteur = secteurService.getSecteurById(id);
        if (secteur == null) {
            System.out.println("le Secteur n'existe pas");
        }
        model.addAttribute("secteur", this.secteurService.getSecteurById(id));
        return "detailSecteur";

    }

    @RequestMapping(value = "/addSecteur", method = RequestMethod.GET)
    public String ajouterSecteur(Model model) {
        model.addAttribute("secteur", new Secteur());
        return "AddSecteur";
    }

    @RequestMapping(value = "/saveSecteur", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute Secteur secteur, Model model, BindingResult result) {

        if (result.hasErrors()) {
            return "AddSecteur";
        } else {
            this.secteurService.saveSecteur(secteur);
            model.addAttribute("secteurs", this.secteurService.getAllSecteurs());
            return "home";
        }
    }

    @RequestMapping(value = "/editionSecteur", method = RequestMethod.GET)
    public String editionSecteur(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("secteur", this.secteurService.getSecteurById(id));
        return "EditSecteur";

    }

    @RequestMapping(value = "/editionSecteur", method = RequestMethod.POST)
    public String editionSecteur(@RequestParam(value = "id") long id, @Valid @ModelAttribute Secteur Secteur, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "EditSecteur";
        } else {
            this.secteurService.saveSecteur(Secteur);
            model.addAttribute("secteurs", this.secteurService.getAllSecteurs());
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/editionSecteur1", method = RequestMethod.GET)
    public String editionSecteur2(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("Secteur", this.secteurService.getSecteurById(id));
        return "editSecteur";

    }

    @RequestMapping(value = "/deleteSecteur1", method = RequestMethod.POST)
    public String deleteSecteur(@RequestParam(value = "id") long id, @Valid @ModelAttribute Secteur secteur, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "EditSecteur";
        } else {
            this.secteurService.deleteSecteurById(secteur.getId());
 //           model.addAttribute("Secteurs", this.secteurService.deleteSecteurById(id));
            return "redirect:/";
        }
    }
}
