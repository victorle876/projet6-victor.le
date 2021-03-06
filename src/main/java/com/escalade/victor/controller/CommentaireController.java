package com.escalade.victor.controller;

import com.escalade.victor.model.Commentaire;
import com.escalade.victor.model.Site;
import com.escalade.victor.model.User_role;
import com.escalade.victor.model.Utilisateur;
import com.escalade.victor.service.CommentaireService;
import com.escalade.victor.service.SiteService;
import com.escalade.victor.service.UtilisateurService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UtilisateurService utilisateurService;

    private static final Logger logger = LogManager.getLogger(CommentaireController.class);

    /**
     * Méthode permet de lister les commentaires
     *
     * * @param id
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
            Utilisateur utilisateurId = this.utilisateurService.getUtilisateurConnected();
            Commentaire commentaireId = this.commentaireService.getCommentaireById(id);
            Site siteId = commentaireId.getSite();
            commentaireId.setUtilisateur(utilisateurId);
            commentaireId.setZoneCommentaire(commentaire.getZoneCommentaire());
            commentaireId.setSite(siteId);
            this.commentaireService.saveCommentaire(commentaireId);
            model.addAttribute("commentaires", this.commentaireService.getAllCommentaires());
            return "redirect:/";
        }
    }

    /**
     * Méthode permet d'effacer le commentaire sur le site en get
     *
     * @param model
     * * @param id
     * *   @return la page "deleteCommentaire" en get
     */
    @RequestMapping(value = "/deleteCommentaire/{id}", method = RequestMethod.GET)
    public String makeCommentaireDeleted(@PathVariable(value = "id") Long id, Model model) {
        logger.info(id);
        Commentaire commentaireId = this.commentaireService.getCommentaireById(id);
        Site siteId = commentaireId.getSite();
        List <Commentaire> commentaireList= this.commentaireService.findCommentaireBySite(siteId);
        model.addAttribute("id", id);
        model.addAttribute("commentairesbysite", commentaireList);
        model.addAttribute("commentaire", commentaireId);
        return "detailsCommentaire";

    }

    /**
     * Méthode permet d'effacer le commentaire sur le site en post
     *
     * @param model
     * @param id
     * *   @return la page "deleteCommentaire" en post
     */
    @RequestMapping(value = "/deleteCommentaire/{id}", method = RequestMethod.POST)
    public String saveCommentaireDeleted(@PathVariable(value = "id") Long id, Model model) {
            Commentaire commentaireId= this.commentaireService.getCommentaireById(id);
            logger.info(commentaireId);
            logger.info("test efface");
            this.commentaireService.deleteCommentaireById(id);
            model.addAttribute("commentaires", this.commentaireService.getAllCommentaires());
            return "redirect:/site/listSiteByUser";
    }
}
