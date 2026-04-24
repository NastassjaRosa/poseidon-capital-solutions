package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Contrôleur gérant les opérations CRUD sur les enchères ({@link BidList}).
 *
 * <p>Expose les endpoints HTTP permettant d'afficher, créer, modifier et supprimer
 * des enchères. Les données sont validées via les annotations de contrainte
 * définies sur l'entité {@link BidList} avant toute persistance.</p>
 *
 * <p>Accessible à tous les utilisateurs authentifiés.</p>
 */

@Controller
public class BidListController {

    private final BidListService bidListService;
    /**
     * Constructeur de BidListController.
     *
     * @param bidListService le service gérant la logique métier des enchères
     */
    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }
    /**
     * Affiche la liste de toutes les enchères.
     *
     * @param model le modèle Thymeleaf alimenté avec la liste des enchères
     * @return la vue {@code bidList/list}
     */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidLists", bidListService.findAll());
        return "bidList/list";
    }
    /**
     * Affiche le formulaire d'ajout d'une nouvelle enchère.
     *
     * @param bidList un objet vide utilisé pour lier les champs du formulaire
     * @return la vue {@code bidList/add}
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bidList) {
        return "bidList/add";
    }
    /**
     * Valide et enregistre une nouvelle enchère.
     *
     * <p>Si la validation échoue, le formulaire d'ajout est réaffiché avec les erreurs.
     * En cas de succès, l'enchère est persistée et l'utilisateur est redirigé
     * vers la liste des enchères.</p>
     *
     * @param bidList l'enchère à valider et sauvegarder
     * @param result  le résultat de la validation des contraintes
     * @param model   le modèle Thymeleaf
     * @return la vue {@code bidList/add} en cas d'erreur, sinon une redirection vers {@code /bidList/list}
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/add";
        }

        bidListService.save(bidList);
        model.addAttribute("bidLists", bidListService.findAll());
        return "redirect:/bidList/list";
    }
    /**
     * Affiche le formulaire de modification d'une enchère existante.
     *
     * @param id    l'identifiant de l'enchère à modifier
     * @param model le modèle Thymeleaf alimenté avec l'enchère à modifier
     * @return la vue {@code bidList/update}
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListService.findById(id);
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }
    /**
     * Valide et met à jour une enchère existante.
     *
     * <p>Si la validation échoue, le formulaire de modification est réaffiché avec les erreurs.
     * En cas de succès, l'enchère est mise à jour et l'utilisateur est redirigé
     * vers la liste des enchères.</p>
     *
     * @param id      l'identifiant de l'enchère à mettre à jour
     * @param bidList les nouvelles données de l'enchère
     * @param result  le résultat de la validation des contraintes
     * @param model   le modèle Thymeleaf
     * @return la vue {@code bidList/update} en cas d'erreur, sinon une redirection vers {@code /bidList/list}
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            bidList.setBidListId(id);
            return "bidList/update";
        }

        bidList.setBidListId(id);
        bidListService.save(bidList);
        model.addAttribute("bidLists", bidListService.findAll());
        return "redirect:/bidList/list";
    }
    /**
     * Supprime une enchère par son identifiant et redirige vers la liste.
     *
     * @param id    l'identifiant de l'enchère à supprimer
     * @param model le modèle Thymeleaf
     * @return une redirection vers {@code /bidList/list}
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.deleteById(id);
        model.addAttribute("bidLists", bidListService.findAll());
        return "redirect:/bidList/list";
    }
}