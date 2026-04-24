package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Contrôleur gérant les opérations CRUD sur les points de courbe ({@link CurvePoint}).
 *
 * <p>Expose les endpoints HTTP permettant d'afficher, créer, modifier et supprimer
 * des points de courbe. Les données sont validées via les annotations de contrainte
 * définies sur l'entité {@link CurvePoint} avant toute persistance.</p>
 *
 * <p>Accessible à tous les utilisateurs authentifiés.</p>
 */
@Controller
public class CurveController {

    private final CurvePointService curvePointService;
    /**
     * Constructeur de CurveController.
     *
     * @param curvePointService le service gérant la logique métier des points de courbe
     */
    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }
    /**
     * Affiche la liste de tous les points de courbe.
     *
     * @param model le modèle Thymeleaf alimenté avec la liste des points de courbe
     * @return la vue {@code curvePoint/list}
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curvePointService.findAll());
        return "curvePoint/list";
    }
    /**
     * Affiche le formulaire d'ajout d'un nouveau point de courbe.
     *
     * @param curvePoint un objet vide utilisé pour lier les champs du formulaire
     * @return la vue {@code curvePoint/add}
     */
    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }
    /**
     * Valide et enregistre un nouveau point de courbe.
     *
     * <p>Si la validation échoue, le formulaire d'ajout est réaffiché avec les erreurs.
     * En cas de succès, le point de courbe est persisté et l'utilisateur est redirigé
     * vers la liste.</p>
     *
     * @param curvePoint le point de courbe à valider et sauvegarder
     * @param result     le résultat de la validation des contraintes
     * @param model      le modèle Thymeleaf
     * @return la vue {@code curvePoint/add} en cas d'erreur, sinon une redirection vers {@code /curvePoint/list}
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/add";
        }

        curvePointService.save(curvePoint);
        model.addAttribute("curvePoints", curvePointService.findAll());
        return "redirect:/curvePoint/list";
    }
    /**
     * Affiche le formulaire de modification d'un point de courbe existant.
     *
     * @param id    l'identifiant du point de courbe à modifier
     * @param model le modèle Thymeleaf alimenté avec le point de courbe à modifier
     * @return la vue {@code curvePoint/update}
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curvePointService.findById(id);
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }
    /**
     * Valide et met à jour un point de courbe existant.
     *
     * @param id         l'identifiant du point de courbe à mettre à jour
     * @param curvePoint les nouvelles données du point de courbe
     * @param result     le résultat de la validation des contraintes
     * @param model      le modèle Thymeleaf
     * @return la vue {@code curvePoint/update} en cas d'erreur, sinon une redirection vers {@code /curvePoint/list}
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            curvePoint.setId(id);
            return "curvePoint/update";
        }

        curvePoint.setId(id);
        curvePointService.save(curvePoint);
        model.addAttribute("curvePoints", curvePointService.findAll());
        return "redirect:/curvePoint/list";
    }
    /**
     * Supprime un point de courbe par son identifiant et redirige vers la liste.
     *
     * @param id    l'identifiant du point de courbe à supprimer
     * @param model le modèle Thymeleaf
     * @return une redirection vers {@code /curvePoint/list}
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        curvePointService.deleteById(id);
        model.addAttribute("curvePoints", curvePointService.findAll());
        return "redirect:/curvePoint/list";
    }
}