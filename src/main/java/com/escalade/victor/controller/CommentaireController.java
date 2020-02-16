package com.escalade.victor.controller;

import com.escalade.victor.model.Commentaire;
import com.escalade.victor.service.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public class CommentaireController {
    @Autowired
    private CommentaireService commentaireService;

    @RequestMapping(value = "/detailsCommentaire", method = RequestMethod.GET)
    public String detail(@RequestParam(value = "id") Long id, Model model) {
        Commentaire commentaire = commentaireService.getCommentaireById(id);
        if (commentaire == null) {
            System.out.println("le commentaire n'existe pas");
        }
        model.addAttribute("commentaire", this.commentaireService.getCommentaireById(id));
        return "detailCommentaire";

    }

    @RequestMapping(value = "/addCommentaire", method = RequestMethod.GET)
    public String ajouterCommentaire(Model model) {
        model.addAttribute("commentaire", new Commentaire());
        return "AddCommentaire";
    }

    @RequestMapping(value = "/saveCommentaire", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute Commentaire commentaire, Model model, BindingResult result) {

        if (result.hasErrors()) {
            return "AddCommentaire";
        } else {
            this.commentaireService.saveCommentaire(commentaire);
            model.addAttribute("commentaires", this.commentaireService.getAllCommentaires());
            return "home";
        }
    }

    @RequestMapping(value = "/editionCommentaire", method = RequestMethod.GET)
    public String editionCommentaire(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("commentaire", this.commentaireService.getCommentaireById(id));
        return "EditCommentaire";

    }

    @RequestMapping(value = "/editionCommentaire", method = RequestMethod.POST)
    public String editionCommentaire(@RequestParam(value = "id") long id, @Valid @ModelAttribute Commentaire commentaire, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editUser";
        } else {
            this.commentaireService.saveCommentaire(commentaire);
            model.addAttribute("commentaires", this.commentaireService.getAllCommentaires());
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/editionCommentaire1", method = RequestMethod.GET)
    public String editionCommentaire2(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("commentaire", this.commentaireService.getCommentaireById(id));
        return "EditCommentaire";

    }

    @RequestMapping(value = "/deleteCommentaire1", method = RequestMethod.POST)
    public String deleteCommentaire(@RequestParam(value = "id") long id, @Valid @ModelAttribute Commentaire Commentaire, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "EditCommentaire";
        } else {
            this.commentaireService.deleteCommentaireById(Commentaire.getId());
        //    model.addAttribute("Commentaires", this.commentaireService.deleteCommentaireById(id));
            return "redirect:/";
        }
    }
}
