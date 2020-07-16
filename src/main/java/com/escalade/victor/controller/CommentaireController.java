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
import javax.websocket.server.PathParam;
import java.util.List;

@RequestMapping("/commentaire")
@Controller
public class CommentaireController {
    @Autowired
    private CommentaireService commentaireService;

    @Autowired
    private SiteService siteService;

    private static final Logger logger = LogManager.getLogger(CommentaireController.class);

    /**
     * Méthode permet de lister les commentaires
     *
     * @param id
     * @param model
     * * @return la page "listCommentaire"
     */
    @RequestMapping(value = "/listCommentaire/{id}", method = RequestMethod.GET)
    public String CommentaireList(@PathVariable("id") Long id, Model model) {
        Site siteId = this.siteService.getSiteById(id);
        List<Commentaire> commentaireList= this.commentaireService.findCommentaireBySite(siteId);
        model.addAttribute("id", id);
        model.addAttribute("commentaires",commentaireList);
        return "listCommentaire";

    }

    /**
     * Méthode permet de voir le commentaire en détail
     *
     * @param id
     * @param model
     * *  @return la page "detailscommentaire"
     */

    @RequestMapping(value = "/detailsCommentaire", method = RequestMethod.GET)
    public String detail(@RequestParam(value = "id") Long id, Model model) {
        Commentaire commentaire = commentaireService.getCommentaireById(id);
        if (commentaire == null) {
            logger.info("le commentaire n'existe pas");
        }
        logger.info(this.commentaireService.getCommentaireById(id));
        model.addAttribute("commentaire", this.commentaireService.getCommentaireById(id));
        return "detailsCommentaire";

    }

    /**
     * Méthode permet d'ajouter le commentaire sur le site en get
     *
     * @param model
     * * *  @return la page "AddCommentaire" en get
     */

    @RequestMapping(value = "/addCommentaire", method = RequestMethod.GET)
    public String ajouterCommentaire(Model model) {
        model.addAttribute("commentaire", new Commentaire());
        return "addCommentaire";
    }

    /**
     * Méthode permet d'ajouter le commentaire sur le site en poist
     *
     * @param model
     * @param commentaire
     * @param result
     * *   @return la page "AddCommentaire" en post
     */
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

    /**
     * Méthode permet de modifier le commentaire sur le site en get
     *
     * @param model
     * @param id
     * *   @return la page "editionCommentaire" en get
     */
    @RequestMapping(value = "/editionCommentaire", method = RequestMethod.GET)
    public String editionCommentaire(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("commentaire", this.commentaireService.getCommentaireById(id));
        return "editionCommentaire";

    }

    /**
     * Méthode permet de modifier le commentaire sur le site en post
     *
     * @param model
     * @param id
     * *   @return la page "editionCommentaire" en post
     */
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

    /**
     * Méthode permet d'effacer le commentaire sur le site en get
     *
     * @param model
     * @param id
     * *   @return la page "deleteCommentaire" en get
     */
    @RequestMapping(value = "/deleteCommentaire", method = RequestMethod.GET)
    public String makeCommentaireDeleted(@RequestParam(value = "id") Long id, Model model) {
        logger.info(id);
        Commentaire commentaireId = this.commentaireService.getCommentaireById(id);
        Site siteId = commentaireId.getSite();
        List <Commentaire> commentaireList= this.commentaireService.findCommentaireBySite(siteId);
        model.addAttribute("id", id);
        model.addAttribute("commentairesbysite", commentaireList);
        model.addAttribute("commentaire", this.commentaireService.getCommentaireById(id));
        return "detailsSite";

    }


    /**
     * Méthode permet d'effacer le commentaire sur le site en post
     *
     * @param model
     * @param id
     * *   @return la page "deleteCommentaire" en post
     */
    @RequestMapping(value = "/deleteCommentaire", method = RequestMethod.POST)
    public String saveCommentaireDeleted(@PathParam(value = "id") Long id, Model model) {
            Commentaire commentaireId= this.commentaireService.getCommentaireById(id);
            logger.info(commentaireId);
            logger.info("test efface");
            this.commentaireService.deleteCommentaireById(id);
            model.addAttribute("commentaires", this.commentaireService.getAllCommentaires());
            return "redirect:/site/listSiteByUser";
    }
}
