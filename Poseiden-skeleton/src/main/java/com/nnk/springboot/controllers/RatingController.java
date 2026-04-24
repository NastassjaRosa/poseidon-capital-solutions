package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Contrôleur gérant les opérations CRUD sur les notations ({@link Rating}).
 *
 * <p>Expose les endpoints HTTP permettant d'afficher, créer, modifier et supprimer
 * des notations financières (Moody's, S&amp;P, Fitch). Les données sont validées
 * via les annotations de contrainte définies sur l'entité {@link Rating}
 * avant toute persistance.</p>
 *
 * <p>Accessible à tous les utilisateurs authentifiés.</p>
 */
@Controller
public class RatingController {

    private final RatingService ratingService;
    /**
     * Constructeur de RatingController.
     *
     * @param ratingService le service gérant la logique métier des notations
     */
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
    /**
     * Affiche la liste de toutes les notations.
     *
     * @param model le modèle Thymeleaf alimenté avec la liste des notations
     * @return la vue {@code rating/list}
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("ratings", ratingService.findAll());
        return "rating/list";
    }
    /**
     * Affiche le formulaire d'ajout d'une nouvelle notation.
     *
     * @param rating un objet vide utilisé pour lier les champs du formulaire
     * @return la vue {@code rating/add}
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }
    /**
     * Valide et enregistre une nouvelle notation.
     *
     * <p>Si la validation échoue, le formulaire d'ajout est réaffiché avec les erreurs.
     * En cas de succès, la notation est persistée et l'utilisateur est redirigé
     * vers la liste.</p>
     *
     * @param rating la notation à valider et sauvegarder
     * @param result le résultat de la validation des contraintes
     * @param model  le modèle Thymeleaf
     * @return la vue {@code rating/add} en cas d'erreur, sinon une redirection vers {@code /rating/list}
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/add";
        }

        ratingService.save(rating);
        model.addAttribute("ratings", ratingService.findAll());
        return "redirect:/rating/list";
    }
    /**
     * Affiche le formulaire de modification d'une notation existante.
     *
     * @param id    l'identifiant de la notation à modifier
     * @param model le modèle Thymeleaf alimenté avec la notation à modifier
     * @return la vue {@code rating/update}
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.findById(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }
    /**
     * Valide et met à jour une notation existante.
     *
     * @param id     l'identifiant de la notation à mettre à jour
     * @param rating les nouvelles données de la notation
     * @param result le résultat de la validation des contraintes
     * @param model  le modèle Thymeleaf
     * @return la vue {@code rating/update} en cas d'erreur, sinon une redirection vers {@code /rating/list}
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            rating.setId(id);
            return "rating/update";
        }

        rating.setId(id);
        ratingService.save(rating);
        model.addAttribute("ratings", ratingService.findAll());
        return "redirect:/rating/list";
    }
    /**
     * Supprime une notation par son identifiant et redirige vers la liste.
     *
     * @param id    l'identifiant de la notation à supprimer
     * @param model le modèle Thymeleaf
     * @return une redirection vers {@code /rating/list}
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.deleteById(id);
        model.addAttribute("ratings", ratingService.findAll());
        return "redirect:/rating/list";
    }
}