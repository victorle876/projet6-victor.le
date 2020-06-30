package com.escalade.victor.controller;

import com.escalade.victor.model.Commentaire;
import com.escalade.victor.model.Site;
import com.escalade.victor.model.Topologie;
import com.escalade.victor.service.CommentaireService;
import com.escalade.victor.service.SiteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/commentaire")
@Controller
public class CommentaireController {
    @Autowired
    private CommentaireService commentaireService;

    @Autowired
    private SiteService siteService;

    private static final Logger logger = LogManager.getLogger(CommentaireController.class);

    @RequestMapping(value = "/listCommentaire/{id}", method = RequestMethod.GET)
    public String CommentaireList(@PathVariable("id") Long id, Model model) {
        Site siteId = this.siteService.getSiteById(id);
        List<Commentaire> commentaireList= this.commentaireService.findCommentaireBySite(siteId);
        model.addAttribute("id", id);
        model.addAttribute("commentaires",commentaireList);
        return "listCommentaire";

    }

    @RequestMapping(value = "/detailsCommentaire", method = RequestMethod.GET)
    public String detail(@RequestParam(value = "id") Long id, Model model) {
        Commentaire commentaire = commentaireService.getCommentaireById(id);
        if (commentaire == null) {
            logger.debug("le commentaire n'existe pas");
        }
        model.addAttribute("commentaire", this.commentaireService.getCommentaireById(id));
        return "detailsCommentaire";

    }

    @RequestMapping(value = "/addCommentaire", method = RequestMethod.GET)
    public String ajouterCommentaire(Model model) {
        model.addAttribute("commentaire", new Commentaire());
        return "addCommentaire";
    }

    @RequestMapping(value = "/saveCommentaire", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute Commentaire commentaire, Model model, BindingResult result) {

        if (result.hasErrors()) {
            return "addCommentaire";
        } else {
            this.commentaireService.saveCommentaire(commentaire);
            model.addAttribute("commentaires", this.commentaireService.getAllCommentaires());
            return "home";
        }
    }

    @RequestMapping(value = "/editionCommentaire", method = RequestMethod.GET)
    public String editionCommentaire(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("commentaire", this.commentaireService.getCommentaireById(id));
        return "editionCommentaire";

    }

    @RequestMapping(value = "/editionCommentaire", method = RequestMethod.POST)
    public String editionCommentaire(@RequestParam(value = "id") long id, @Valid @ModelAttribute Commentaire commentaire, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "editionCommentaire";
        } else {
            this.commentaireService.saveCommentaire(commentaire);
            model.addAttribute("commentaires", this.commentaireService.getAllCommentaires());
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/deleteCommentaire/{id}", method = RequestMethod.GET)
    public String makeCommentaireDeleted(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("commentaire", this.commentaireService.getCommentaireById(id));
        return "listCommentaire";

    }

    @RequestMapping(value = "/deleteCommentaire/{id}", method = RequestMethod.POST)
    public String saveCommentaireDeleted(@PathVariable(value = "id") Long id, Model model) {
            Commentaire commentaireId= this.commentaireService.getCommentaireById(id);
            logger.debug(commentaireId);
            this.commentaireService.deleteCommentaireById(id);
            model.addAttribute("commentaires", this.commentaireService.getAllCommentaires());
            return "redirect:/site/listSiteByUser";
    }
}
