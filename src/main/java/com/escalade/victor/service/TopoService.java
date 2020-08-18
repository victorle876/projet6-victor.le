package com.escalade.victor.service;

import com.escalade.victor.model.Site;
import com.escalade.victor.model.Topo;
import com.escalade.victor.model.Utilisateur;
import com.escalade.victor.repository.SecteurRepository;
import com.escalade.victor.repository.SiteRepository;
import com.escalade.victor.repository.TopoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopoService {

    @Autowired
    TopoRepository TopoRepository;

    @Autowired
    SiteRepository siteRepository;

    private static final Logger logger = LogManager.getLogger(TopoService.class);

    /**
     * Méthode permet de lister toutes les Topos via ce service
     *
     * * @return la liste des topos
     */
    public List<Topo> getAllTopos()
    {
        List<Topo> TopoList = TopoRepository.findAll();
        logger.debug(TopoList.size());
        if(TopoList.size() > 0) {
            return TopoList;
        } else {
            return new ArrayList<Topo>();
        }
    }

    /**
     * Méthode permet de consulter le topo en fonction de l'id via ce service
     *
     * @param id
     * * @return le topo via id
     */
    public Topo getTopoById(Long id)
    {
       return this.TopoRepository.findById(id).get();

    }

    /**
     * Méthode permet de sauvegarder le topo via ce service
     *
     * @param Topo
     * * @return le topo sauvegardé
     */
    public Topo saveTopo(Topo Topo)
    {
        return this.TopoRepository.save(Topo);

    }

    /**
     * Méthode permet de trouver la liste des sites en fonction de l'utilisateur via ce service
     *
     * @param utilisateur1
     * @return la liste
     */
    public List<Site> findSiteByUser(Utilisateur utilisateur1) throws UsernameNotFoundException {
        List<Site>  siteTrouve = this.siteRepository.findByUtilisateurAndTopoIsNull(utilisateur1);
        if (siteTrouve == null){
            throw new RuntimeException("Site introuvable");
        }
         return siteTrouve;
    }

    /**
     * Méthode permet de trouver la liste des topos en fonction de l'utilisateur via ce service
     *
     * @param utilisateur1
     * @return la liste
     */
    public List<Topo> findTopoByUser(Utilisateur utilisateur1) throws UsernameNotFoundException {
        List<Topo>  TopoTrouve = this.TopoRepository.findByUtilisateur(utilisateur1);
        if (TopoTrouve == null){
            throw new RuntimeException("Topo introuvable");
        }
        return TopoTrouve;
    }

    /**
     * Méthode permet de trouver la liste des topos en fonction de l'utilisateur autre  via ce service
     *
     * @param utilisateur1
     * @return la liste
     */
    public List<Topo> findTopoByUserDifferent(Utilisateur utilisateur1) throws UsernameNotFoundException {
        List<Topo>  TopoTrouve = this.TopoRepository.findByUtilisateurNot(utilisateur1);
        if (TopoTrouve == null){
            throw new RuntimeException("Topo introuvable");
        }
        return TopoTrouve;
    }

    /**
     * Méthode permet de trouver la liste des topos en fonction des critères de recherche via ce service
     *
     * @param recherche
     * @return la liste
     */
    public List<Topo> findTopoBySecteurOrNom(String recherche) throws UsernameNotFoundException {
        List<Topo>  TopoTrouve = this.TopoRepository.findByNomTopologieIgnoreCaseContaining(recherche);
        logger.debug(TopoTrouve);
        if (TopoTrouve == null){
            throw new RuntimeException("Topo introuvable");
        }
        return TopoTrouve;
    }

    /**
     * Méthode permet de trouver la liste des topos en fonction du boolean Public via ce service
     *
     *
     * @return la liste
     */
    public List<Topo> findTopoByPublic() throws UsernameNotFoundException {
        List<Topo>  TopoPublic = this.TopoRepository.findByIspublicTrue();
        logger.debug(TopoPublic);
        if (TopoPublic == null){
            throw new RuntimeException("Topo introuvable");
        }
        return TopoPublic;
    }

    /**
     * Méthode permet de trouver la liste des topos publiques en fonction du boolean Public via ce service
     *
     * @param utilisateur1
     * @return la liste
     */
    public List<Topo> findTopoByPublicAndIspublic(Utilisateur utilisateur1) throws UsernameNotFoundException {
        List<Topo>  TopoPublicUser = this.TopoRepository.findByAndIspublicTrueAndUtilisateurNot(utilisateur1);
        logger.debug(TopoPublicUser);
        if (TopoPublicUser == null){
            throw new RuntimeException("Topo introuvable");
        }
        return TopoPublicUser;
    }

    public List<Topo> findTopoByIspublic() throws UsernameNotFoundException {
        List<Topo>  TopoPublicUser = this.TopoRepository.findByIspublicTrue();
        logger.debug(TopoPublicUser);
        if (TopoPublicUser == null){
            throw new RuntimeException("Topo introuvable");
        }
        return TopoPublicUser;
    }

    /**
     * Méthode permet d'effacer le topo en fonction de l'id via ce service
     *
     * @param id
     */
    public void deleteToposById(Long id)
    {
           TopoRepository.deleteById(id);
    }

}

